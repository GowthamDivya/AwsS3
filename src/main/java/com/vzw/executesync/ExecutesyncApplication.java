package com.vzw.executesync;

import com.vzw.executesync.common.entities.ExecsyncConfig;
import com.vzw.executesync.common.repositories.ExecsyncConfigRepository;
import com.vzw.executesync.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExecutesyncApplication implements CommandLineRunner {

    @Autowired
    ExecsyncConfigRepository execsyncConfigRepository;

    @Autowired
    private SyncService myService;

    public static void main(String[] args) {
        SpringApplication.run(ExecutesyncApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        myService.performSync(3, 1);
    }


//
}
