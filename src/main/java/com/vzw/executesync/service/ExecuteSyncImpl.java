package com.vzw.executesync.service;

import com.vzw.executesync.common.entities.ExecsyncConfig;
import com.vzw.executesync.common.entities.FileIngestion;
import com.vzw.executesync.common.repositories.ExecsyncConfigRepository;
import com.vzw.executesync.common.repositories.FileIngestionRepository;
import com.vzw.executesync.execync.AbstractExecuteSync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public abstract class ExecuteSyncImpl extends AbstractExecuteSync {

    @Autowired
    private FileIngestionRepository fileIngestionRepository;

    @Autowired
    private ExecsyncConfigRepository execsyncConfigRepository;

    @Autowired
    private S3Client s3Client;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Override
    public void executeSync() {
        // Fetch the configuration for the given ID (153 in this case)
        ExecsyncConfig execsyncConfig = execsyncConfigRepository.findById(153)
                .orElseThrow(() -> new IllegalArgumentException("ExecsyncConfig not found for ID: 153"));

        try {
            // Update status to "Starting"
            updateStatus(execsyncConfig.getId(), "Starting");

            // Fetch the file path from configuration
            String emsRawfilePath = execsyncConfig.getEmsRawfilePath();

            if (emsRawfilePath != null && !emsRawfilePath.isEmpty()) {
                // Update status to "In Progress"
                updateStatus(execsyncConfig.getId(), "In Progress");

                // Upload the file to S3
                copytoS3(execsyncConfig);

                // Update status to "Completed"
                updateStatus(execsyncConfig.getId(), "Completed");

                // Push metadata to Kafka topic
                metadataTopicPush(execsyncConfig);
            } else {
                // Update status to "Failed" if file path is null or empty
                updateStatus(execsyncConfig.getId(), "Failed");
                System.err.println("File path is null or empty.");
            }
        } catch (Exception e) {
            // Update status to "Failed" in case of any exceptions
            updateStatus(execsyncConfig.getId(), "Failed");
            System.err.println("Execution failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateStatus(Integer id, String status) {
        FileIngestion fileIngestion = fileIngestionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("FileIngestion not found for ExecsyncConfig ID: " + id));
        fileIngestion.setStatus(status);
        fileIngestionRepository.save(fileIngestion);
    }

    @Override
    public void copytoS3(ExecsyncConfig execsyncConfig) throws IOException {
        // Read the file content into a byte array
        byte[] fileContent = Files.readAllBytes(Paths.get(execsyncConfig.getEmsRawfilePath()));

        // Build the PutObjectRequest using the S3 configuration from ExecsyncConfig
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(execsyncConfig.getS3BucketPath()) // Bucket name
                .key(execsyncConfig.getS3SecretKey())     // Object key (file name in S3)
                .build();

        // Upload the file to S3
        PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileContent));

        // Optionally log the result
        System.out.println("Uploaded to S3 bucket " + execsyncConfig.getS3BucketPath() +
                " with key " + execsyncConfig.getS3SecretKey() + ", ETag: " + response.eTag());
    }



    @Override
    public void metadataTopicPush(ExecsyncConfig execsyncConfig) {
        // Generate metadata message
        String metadataMessage = generateMetadataMessage(execsyncConfig);

        // Send metadata to Kafka topic using KafkaProducerService
        kafkaProducerService.sendMessage(metadataMessage);
    }

    private String generateMetadataMessage(ExecsyncConfig execsyncConfig) {
        // Example of creating a metadata message. Customize as needed.
        return String.format("File uploaded to S3: Bucket [%s], Key [%s], by EMS [%s] in Region [%s]",
                execsyncConfig.getS3BucketPath(),
                execsyncConfig.getS3SecretKey(),
                execsyncConfig.getEmsName(),
                execsyncConfig.getRocRegion());
    }

    @Override
    public void readFromSource(ExecsyncConfig execsyncConfig) throws IOException {
        // Check if the path points to a file or a directory
        Path sourcePath = Paths.get(execsyncConfig.getEmsRawfilePath());

        if (Files.isDirectory(sourcePath)) {
            // If it's a directory, list and process all files in the directory
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourcePath)) {
                for (Path file : stream) {
                    if (Files.isRegularFile(file)) {
                        // Process each file (example: read and log file name)
                        System.out.println("Found file: " + file.toString());
                    }
                }
            }
        } else if (Files.isRegularFile(sourcePath)) {
            // If it's a single file, process the file
            System.out.println("Found single file: " + sourcePath.toString());
        } else {
            throw new IOException("The specified path is neither a file nor a directory: " + sourcePath);
        }
    }
}
