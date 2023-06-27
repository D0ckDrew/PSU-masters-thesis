package com.psu.producer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.psu.producer.model.KafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaMessageService {

    private final KafkaProducer producer;

    @Autowired
    public KafkaMessageService(KafkaProducer producer) {
        this.producer = producer;
    }

    public String createMessage(KafkaMessage message) throws JsonProcessingException {
        return producer.sendMessage(message);
    }
}
