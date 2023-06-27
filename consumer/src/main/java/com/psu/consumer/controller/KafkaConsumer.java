package com.psu.consumer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.psu.consumer.model.KafkaMessage;
import com.psu.consumer.service.KafkaConsumerService;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {
    private static final String orderTopic = "${kafka.topic}";
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;
    private final KafkaConsumerService service;
    @Autowired
    public KafkaConsumer(ObjectMapper objectMapper, ModelMapper modelMapper, KafkaConsumerService service) {
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
        this.service = service;
    }

    @Timed(value = "kafka.consumer.processing.time", percentiles = {0.75, 0.90, 0.95})
    @KafkaListener(topics = orderTopic)
    public void consumeMessage(String message) throws JsonProcessingException {
        KafkaMessage kafkaMessage = objectMapper.readValue(message, KafkaMessage.class);

        long dataSum = 0;
        if (!service.hasSourceIgnoring(kafkaMessage.getSource())) {
            int iterations = kafkaMessage.getSource();
            for (int i = 0; i < iterations; i++) {
                for (char ch: kafkaMessage.getData().toCharArray()) {
                    dataSum += (int) ch;
                }
            }
        }

        log.info("MESSAGE | source: {} | data {} | dataSum {}", kafkaMessage.getSource(), kafkaMessage.getData(), dataSum);
    }
}
