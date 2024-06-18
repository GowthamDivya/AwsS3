package com.vzw.executesync.service;

import com.vzw.executesync.common.entities.ExecsyncConfig;
import com.vzw.executesync.common.repositories.ExecsyncConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExectuSyncService {
    @Autowired
    ExecsyncConfigRepository execsyncConfigRepository;

    public ExecsyncConfig save(ExecsyncConfig execsyncConfig) {
        return execsyncConfigRepository.save(execsyncConfig);
    }

}
