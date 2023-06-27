package com.psu.producer.model;

import lombok.Data;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Data
@Value
public class KafkaMessage {
    String data;
    Integer source;

    public static KafkaMessage generate(List<Integer> sources) {
        String generatedData = UUID.randomUUID().toString();

        int generatedSource = 0;
        if (sources.size() == 1) {
            generatedSource = sources.get(0);
        } else if (sources.size() > 1) {
            int randomIndex = (int) (Math.random() * sources.size());
            generatedSource = sources.get(randomIndex);
        }

        return new KafkaMessage(generatedData, generatedSource);
    }
}
