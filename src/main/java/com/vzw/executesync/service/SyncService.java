package com.vzw.executesync.service;

import com.vzw.executesync.common.entities.ExecsyncConfig;
import com.vzw.executesync.execync.ExecuteSyncImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SyncService {

    @Autowired
    private ExecuteSyncImpl executeSync;

    @Autowired
    private S3FileUploadService s3FileUploadService;

    @Value("aws.s3.bucketName")
    String bucket;

    @PostConstruct
    public void performSync(Integer configId, Integer fileIngestionId) throws IOException {

        ExecsyncConfig execsyncConfig = executeSync.readFromSource(configId);

        // Step 2: Update status to "in progress"
        executeSync.updateStatus(execsyncConfig.getId(), "in progress");

        s3FileUploadService.uploadFilesInDirectory(bucket, execsyncConfig.getEmsRawfilePath());

        // Step 3: Copy the file to S3
        //  String metadata = executeSync.copyToS3(execsyncConfig);

        // Step 4: Update status to "completed"
        executeSync.updateStatus(fileIngestionId, "completed");

        // Step 5: Push metadata to Kafka topic
        // executeSync.metadataTopicPush(execsyncConfig, metadata);
    }
}
