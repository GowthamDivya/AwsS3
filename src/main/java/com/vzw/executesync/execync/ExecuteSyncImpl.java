package com.vzw.executesync.execync;

import com.vzw.executesync.common.entities.ExecsyncConfig;
import com.vzw.executesync.common.repositories.ExecsyncConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExecuteSyncImpl extends AbstractExecuteSync {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ExecsyncConfigRepository execsyncConfigRepository;

    @Autowired
    public ExecuteSyncImpl(KafkaTemplate<String, String> kafkaTemplate, ExecsyncConfigRepository execsyncConfigRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.execsyncConfigRepository = execsyncConfigRepository;
    }

    @Override
    public String copyToS3(ExecsyncConfig config) {
        S3Client s3Client = createS3Client(config);

        try {
            Path filePath = Paths.get(config.getEmsRawfilePath());
            String s3Key = filePath.getFileName().toString();

            // Prepare the PutObjectRequest
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(config.getS3BucketPath())
                    .key(s3Key)
                    .build();

            // Upload the file to S3
            PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, filePath);

            // Extract metadata from the response
            String metadata = extractMetadata(putObjectResponse, filePath);

            // Optionally, push metadata to Kafka
            metadataTopicPush(config, metadata);

            return metadata;
        } catch (S3Exception | IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    private S3Client createS3Client(ExecsyncConfig config) {
        return S3Client.builder()
                .region(Region.US_EAST_1) // Use the appropriate region from your configuration
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                                config.getS3AccessKey(),
                                config.getS3SecretKey()
                        )
                ))
                .build();
    }

    private String extractMetadata(PutObjectResponse response, Path filePath) throws IOException {
        // Prepare a map to collect metadata
        Map<String, String> metadataMap = new HashMap<>();
        metadataMap.put("ETag", response.eTag());

        // Get file size
        long fileSize = Files.size(filePath);
        metadataMap.put("FileSize", String.valueOf(fileSize));

        // Convert metadata map to a string format
        StringBuilder metadataBuilder = new StringBuilder();
        metadataMap.forEach((key, value) -> metadataBuilder.append(key).append(": ").append(value).append("\n"));

        return metadataBuilder.toString();
    }

    @Override
    public void metadataTopicPush(ExecsyncConfig config, String metadata) {
        kafkaTemplate.send(config.getKafkaTopicName(), metadata);
    }


}
