package com.psu.producer.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${kafka.address}")
    private String kafkaAddress;

    @Value("${kafka.topic}")
    private String kafkaTopic;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        return new KafkaAdmin(configs);
    }
    @Bean
    public NewTopic topic() {
        return TopicBuilder
                .name(kafkaTopic)
                .partitions(10)
                .replicas(1)
                .build();
    }







//
//    @Bean
//    public Map<String, Object> producerConfig() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerUrl);
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        props.put(JsonSerializer.TYPE_MAPPINGS, "Employee:dev.fullstackcode.kafka.producer.dto.Employee");
//        return props;
//    }
//
//    @Bean
//    public ProducerFactory<Integer, Employee> producerFactory() {
//        return new DefaultKafkaProducerFactory<>(producerConfig());
//    }
//
//
//    @Bean
//    public KafkaTemplate<Integer, Employee> kafkaTemplate(ProducerFactory<Integer, Employee> producerFactory) {
//        return new KafkaTemplate<Integer, Employee>(producerFactory);
//    }
//
//    private final KafkaProperties kafkaProperties;
//
//    @Autowired
//    public KafkaProducerConfig(KafkaProperties kafkaProperties) {
//        this.kafkaProperties = kafkaProperties;
//    }
//
//    @Bean
//    public ProducerFactory<String, String> producerFactory() {
//        // get configs on application.properties/yml
//        Map<String, Object> properties = kafkaProperties.buildProducerProperties();
//        return new DefaultKafkaProducerFactory<>(properties);
//    }
//
//    @Bean
//    public KafkaTemplate<String, String> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
}
