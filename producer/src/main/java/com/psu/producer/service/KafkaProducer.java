package com.psu.producer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.psu.producer.model.KafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaProducer {
    @Value("${kafka.topic}")
    private String kafkaTopic;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public String sendMessage(KafkaMessage message) throws JsonProcessingException {
        String orderAsMessage = objectMapper.writeValueAsString(message);
        kafkaTemplate.send(kafkaTopic, orderAsMessage);
        log.info("message produced {}", orderAsMessage);

        return "message sent";
    }
}
