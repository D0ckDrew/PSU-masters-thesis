package com.psu.consumer.model;

import lombok.Data;
import lombok.Value;

@Data
@Value
public class KafkaMessage {
    String data;
    Integer source;
}
