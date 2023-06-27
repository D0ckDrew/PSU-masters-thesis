package com.psu.producer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.psu.producer.model.KafkaMessage;
import com.psu.producer.service.KafkaMessageService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/message")
public class KafkaMessageController {

    private final KafkaMessageService kafkaMessageService;

    @Autowired
    public KafkaMessageController(KafkaMessageService kafkaMessageService) {
        this.kafkaMessageService = kafkaMessageService;
    }

    @PostMapping
    public String createMessage(@RequestBody KafkaMessage message) throws JsonProcessingException {
        log.info("createMessage request received");
        return kafkaMessageService.createMessage(message);
    }

    @PostMapping(value = "/generateRandom")
    public void generateRandom(@RequestParam long count,
                               @RequestParam List<Integer> sources,
                               HttpServletResponse response) throws IOException {
        java.io.PrintWriter wr = response.getWriter();
        response.setStatus(HttpServletResponse.SC_OK);
        wr.print("generateRandom request received");
        wr.flush();
        wr.close();

        for (int i = 0; i < count; i++) {
            kafkaMessageService.createMessage(KafkaMessage.generate(sources));
        }
        log.info("generateRandom request received");

        //return "generateRandom request received";
    }

}
