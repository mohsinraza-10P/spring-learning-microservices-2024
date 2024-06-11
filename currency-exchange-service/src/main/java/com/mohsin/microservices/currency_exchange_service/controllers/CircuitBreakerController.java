package com.mohsin.microservices.currency_exchange_service.controllers;

import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private final Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
    @Retry(name = "default", fallbackMethod = "hardcodedResponse")
    public String sampleApi() {
        logger.info("Sample API called received!");
        var data = new RestTemplate().getForEntity("http://localhost:8000/dummy", String.class);
        return data.getBody();
    }

    public String hardcodedResponse(Exception ex) {
        return "Fallback Response";
    }
}
