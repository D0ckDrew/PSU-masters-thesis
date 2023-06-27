package com.psu.consumer.controller;

import com.psu.consumer.service.KafkaConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class KafkaConsumerHandler {
    private final KafkaConsumerService service;
    @Autowired
    public KafkaConsumerHandler(KafkaConsumerService service) {
        this.service = service;
    }

    @PostMapping(value="/addSourceIgnoring")
    public String addSourceIgnoring(@RequestParam int source)  {
        service.addSourceIgnoring(source);
        log.info("addSourceIgnoring: " + source);
        return "addSourceIgnoring: " + source;
    }

    @PostMapping(value="/clearIgnoringList")
    public String clearIgnoringList()  {
        service.clearIgnoringList();
        log.info("clearIgnoringList");
        return "clearIgnoringList";
    }
}
