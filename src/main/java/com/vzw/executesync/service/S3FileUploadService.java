package com.vzw.executesync.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Service
public class S3FileUploadService {

    private final S3Client s3Client;
    private final KafkaProducerService  kafkaProducerService;

    @Autowired
    public S3FileUploadService(S3Client s3Client, KafkaProducerService kafkaProducerService) {
        this.s3Client = s3Client;
        this.kafkaProducerService = kafkaProducerService;
    }

    public void uploadFilesInDirectory(String bucketName, String directoryPath) throws IOException {
        Path directory = Paths.get(directoryPath);

        try (Stream<Path> paths = Files.walk(directory)) {
            paths.filter(Files::isRegularFile)
                 .forEach(filePath -> {
                     try {
                         uploadFile(bucketName, directory.relativize(filePath).toString(), filePath.toUri());
                     } catch (IOException e) {
                         e.printStackTrace(); // Handle or log the exception as needed
                     }
                 });
        }
    }

    private void uploadFile(String bucketName, String key, java.net.URI filePathURI) throws IOException {
        byte[] fileContent = Files.readAllBytes(Paths.get(filePathURI));

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileContent));
        System.out.println("Uploaded " + key + ", ETag: " + response.eTag());
    }

    // Method to upload all files in a directory asynchronously
    @Async
    public void uploadFilesInDirectoryAsync(String bucketName, String directoryPath) {
        try {
            Path directory = Path.of(directoryPath);

            // Iterate through all files in the directory
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
                for (Path filePath : stream) {
                    if (!Files.isDirectory(filePath)) { // Upload only files, not directories
                        String message = uploadFileAsync(bucketName, directory.relativize(filePath).toString(), filePath).join();
                        sendMetadataToKafkaAsync(message);
                    }
                }
            }

            CompletableFuture.completedFuture(null);
        } catch (IOException e) {
            CompletableFuture.failedFuture(e);
        }
    }


    // Method to upload a single file asynchronously
    private CompletableFuture<String> uploadFileAsync(String bucketName, String key, Path filePath) {
        try {
            // Use Files.newInputStream to read file contents in a buffered manner
            try (var inputStream = Files.newInputStream(filePath)) {
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build();

                PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, Files.size(filePath)));
                return CompletableFuture.completedFuture(response.eTag());
            }
        } catch (IOException e) {
            return CompletableFuture.failedFuture(e);
        }
    }


    private void sendMetadataToKafkaAsync(String uploadedFileKey) {
        CompletableFuture.runAsync(() -> {
            try {
                // Construct metadata message
                String metadataToSend = "Metadata for file: " + uploadedFileKey;

                kafkaProducerService.sendMessage(metadataToSend); // Send metadata to Kafka topic via Kafka producer service
            } catch (Exception e) {
                e.printStackTrace(); // Handle or log the exception as needed
            }
        });
    }



}
