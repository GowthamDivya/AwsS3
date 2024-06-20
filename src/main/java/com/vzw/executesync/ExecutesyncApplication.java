package com.vzw.executesync;

import com.vzw.executesync.common.entities.ExecsyncConfig;
import com.vzw.executesync.common.repositories.ExecsyncConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class ExecutesyncApplication implements CommandLineRunner {

    @Autowired
    ExecsyncConfigRepository execsyncConfigRepository;

    public static void main(String[] args) {
        SpringApplication.run(ExecutesyncApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        Optional<ExecsyncConfig> byId = execsyncConfigRepository.findById(1);
        if (byId.isPresent()) {
            System.out.println(byId.get());
        } else {
            System.out.println("byId = is not found");
        }


    }
}
