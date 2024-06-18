package com.vzw.executesync.execync;

import com.vzw.executesync.common.entities.ExecsyncConfig;

import java.io.IOException;

public interface IExecuteSync {
    void executeSync() throws IOException;
    void copytoS3(ExecsyncConfig execsyncConfig) throws IOException;
    void metadataTopicPush();
    void readFromSource();
}
