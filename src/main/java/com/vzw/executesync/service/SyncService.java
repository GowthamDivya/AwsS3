package com.vzw.executesync.service;

import com.vzw.executesync.common.entities.ExecsyncConfig;
import com.vzw.executesync.execync.ExecuteSyncImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SyncService {

    @Autowired
    private ExecuteSyncImpl executeSync;

    public void performSync(Integer configId, Integer fileIngestionId) {
        // Step 1: Read the source file path


        ExecsyncConfig execsyncConfig = executeSync.readFromSource(configId);
        
        // Step 2: Update status to "in progress"
        executeSync.updateStatus(execsyncConfig.getId(), "in progress");

        // Step 3: Copy the file to S3
        String metadata = executeSync.copyToS3(execsyncConfig);

        // Step 4: Update status to "completed"
        executeSync.updateStatus(fileIngestionId, "completed");

        // Step 5: Push metadata to Kafka topic
        executeSync.metadataTopicPush(execsyncConfig,metadata);
    }
}
