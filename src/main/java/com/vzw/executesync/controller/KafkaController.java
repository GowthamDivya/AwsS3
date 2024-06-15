package com.vzw.executesync.controller;

import com.vzw.executesync.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public KafkaController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessageToKafka(@RequestBody String message) {
        kafkaProducerService.sendMessage(message);
        return ResponseEntity.ok("Message sent to Kafka topic: " + message);
    }
}
