package com.vzw.executesync.execync;

import com.vzw.executesync.common.entities.ExecsyncConfig;

import java.io.IOException;

public interface IExecuteSync {
    String copyToS3(ExecsyncConfig filePath) throws IOException;

    void metadataTopicPush(ExecsyncConfig config ,String metadata);

    ExecsyncConfig readFromSource(Integer configId);


    void updateStatus(Integer fileIngestionId, String status);
}

