package com.psu.consumer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class KafkaConsumerService {
    private List<Integer> ignoringSources = new ArrayList<>();

    public void clearIgnoringList() {
        ignoringSources.clear();
    }

    public void addSourceIgnoring(int source) {
        if (!hasSourceIgnoring(source)) {
            ignoringSources.add(source);
        }
    }

    public boolean hasSourceIgnoring(int source) {
        return ignoringSources.contains(source);
    }
}
