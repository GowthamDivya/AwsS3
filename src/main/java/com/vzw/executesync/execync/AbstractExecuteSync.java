package com.vzw.executesync.execync;

import com.vzw.executesync.common.entities.ExecsyncConfig;
import com.vzw.executesync.execync.IExecuteSync;

import java.io.IOException;

public abstract class AbstractExecuteSync implements IExecuteSync {
    public abstract void metadataTopicPush(ExecsyncConfig execsyncConfig);

    public abstract void readFromSource(ExecsyncConfig execsyncConfig) throws IOException;
    // Common methods or properties for sync operations
}
