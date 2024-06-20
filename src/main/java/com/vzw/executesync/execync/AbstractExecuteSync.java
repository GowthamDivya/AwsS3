package com.vzw.executesync.execync;

import com.vzw.executesync.common.entities.ExecsyncConfig;
import com.vzw.executesync.common.entities.FileIngestion;
import com.vzw.executesync.common.repositories.ExecsyncConfigRepository;
import com.vzw.executesync.common.repositories.FileIngestionRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractExecuteSync implements IExecuteSync {
    @Autowired
    private ExecsyncConfigRepository execsyncConfigRepository;

    @Autowired
    private FileIngestionRepository fileIngestionStatusRepository;

    @Override
    public ExecsyncConfig readFromSource(Integer configId) {
        // Read the file path from ExecsyncConfig table
        return execsyncConfigRepository.findById(configId).orElseThrow(
                () -> new IllegalArgumentException("Invalid config ID: " + configId)
        );
    }

    @Override
    public void updateStatus(Integer fileIngestionId, String status) {
        // Update the status in FileIngestionStatus table
        FileIngestion fileIngestionStatus = fileIngestionStatusRepository.findById(fileIngestionId).orElseThrow(
                () -> new IllegalArgumentException("Invalid file ingestion ID: " + fileIngestionId)
        );
        fileIngestionStatus.setStatus(status);
        fileIngestionStatusRepository.save(fileIngestionStatus);
    }

    public abstract String copyToS3(ExecsyncConfig config);


    public abstract void metadataTopicPush(ExecsyncConfig config, String metadata);
}
