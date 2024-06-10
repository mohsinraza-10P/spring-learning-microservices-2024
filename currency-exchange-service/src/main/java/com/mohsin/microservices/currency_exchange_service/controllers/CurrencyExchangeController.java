package com.mohsin.microservices.currency_exchange_service.controllers;

import com.mohsin.microservices.currency_exchange_service.bean.CurrencyExchange;
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

    @Autowired
    private Environment environment;

    // http://localhost:8000/currency-exchange/from/USD/to/PKR
    // To run more instances of this app on other ports, create new configuration in Intellij and add following to VM option
    // -Dserver.port=8001

    @GetMapping("/from/{fromCurrency}/to/{toCurrency}")
    public CurrencyExchange getExchangeValue(
            @PathVariable String fromCurrency,
            @PathVariable String toCurrency
    ) {
        var currencyExchange = new CurrencyExchange(1L, "USD", "PKR", BigDecimal.valueOf(278.00));
        String port = environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);
        return currencyExchange;
    }
}
