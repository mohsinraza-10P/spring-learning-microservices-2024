package com.mohsin.microservices.currency_exchange_service.controllers;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
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
    // @Retry(name = "default", fallbackMethod = "hardcodedResponse")
    // @CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    // @RateLimiter(name = "default")
    @Bulkhead(name = "default")
    public String sampleApi() {
        logger.info("Sample API called received!");
        return "Sample API";
        // var data = new RestTemplate().getForEntity("http://localhost:8000/dummy", String.class);
        // return data.getBody();
    }

    public String hardcodedResponse(Exception ex) {
        return "Fallback Response";
    }
}
