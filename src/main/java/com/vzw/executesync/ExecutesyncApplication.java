package com.vzw.executesync;

import com.vzw.executesync.common.entities.ExecsyncConfig;
import com.vzw.executesync.common.repositories.ExecsyncConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExecutesyncApplication  {

    @Autowired
    ExecsyncConfigRepository execsyncConfigRepository;

    public static void main(String[] args) {
        SpringApplication.run(ExecutesyncApplication.class, args);
    }


//
}
