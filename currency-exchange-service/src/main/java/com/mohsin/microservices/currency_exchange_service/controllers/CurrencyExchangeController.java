package com.mohsin.microservices.currency_exchange_service.controllers;

import com.mohsin.microservices.currency_exchange_service.bean.CurrencyExchange;
import com.mohsin.microservices.currency_exchange_service.repositories.CurrencyExchangeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("currency-exchange")
public class CurrencyExchangeController {

    private final Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);

    @Autowired
    private Environment environment;

    @Autowired
    private CurrencyExchangeRepository repository;

    // http://localhost:8000/currency-exchange/from/USD/to/PKR
    // To run more instances of this app on other ports, create new configuration in Intellij and add following to VM option
    // -Dserver.port=8001

    @GetMapping("/from/{fromCurrency}/to/{toCurrency}")
    public CurrencyExchange getExchangeValue(
            @PathVariable String fromCurrency,
            @PathVariable String toCurrency
    ) {
        // 2024-06-12T16:58:59.846+05:00  INFO [currency-exchange-service,79c5ca7347227abdfd25fa03ca6fc50a,d978e5f15088d0e4] 90890 --- [currency-exchange-service] [nio-8000-exec-1] [79c5ca7347227abdfd25fa03ca6fc50a-d978e5f15088d0e4] c.m.m.c.c.CurrencyExchangeController     : getExchangeValue called with AUD to PKR
        logger.info("getExchangeValue called with {} to {}", fromCurrency, toCurrency);
        var currencyExchange = repository.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency);
        if (currencyExchange == null) {
            throw new RuntimeException("Unable to find data for " + fromCurrency + " to " + toCurrency);
        }
        String port = environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);
        return currencyExchange;
    }
}
