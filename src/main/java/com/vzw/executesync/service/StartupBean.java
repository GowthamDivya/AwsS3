package com.vzw.executesync.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartupBean {
    @Autowired
    private SyncService myService;


    @PostConstruct
    public void init() {
        // Call your service method here
        myService.performSync(3, 1);
    }
}
