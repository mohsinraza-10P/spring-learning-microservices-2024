package com.mohsin.microservices.limits_service.controllers;

import com.mohsin.microservices.limits_service.bean.Limits;
import com.mohsin.microservices.limits_service.configuration.LimitsConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("limits")
public class LimitsController {

    @Autowired
    private LimitsConfiguration configuration;

    @GetMapping
    public Limits getLimits() {
        return new Limits(configuration.getMinimum(), configuration.getMaximum());
    }
}
