package com.mohsin.microservices.currency_conversion_service.controllers;

import com.mohsin.microservices.currency_conversion_service.bean.CurrencyConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("currency-conversion")
public class CurrencyConversionController {

    @Autowired
    private Environment environment;

    // http://localhost:8100/currency-conversion/from/USD/to/PKR/quantity/10
    // To run more instances of this app on other ports, create new configuration in Intellij and add following to VM option
    // -Dserver.port=81001

    @GetMapping("/from/{fromCurrency}/to/{toCurrency}/quantity/{quantity}")
    public CurrencyConversion getExchangeValue(
            @PathVariable String fromCurrency,
            @PathVariable String toCurrency,
            @PathVariable BigDecimal quantity
    ) {
        var currencyConversion = new CurrencyConversion(1L, fromCurrency, toCurrency, quantity);
        if (currencyConversion == null) {
            throw new RuntimeException("Unable to find data for " + fromCurrency + " to " + toCurrency);
        }
        currencyConversion.setConversionMultiple(BigDecimal.valueOf(10.0));
        currencyConversion.setTotalCalculatedAmount(currencyConversion.getConversionMultiple().multiply(currencyConversion.getQuantity()));
        String port = environment.getProperty("local.server.port");
        currencyConversion.setEnvironment(port);
        return currencyConversion;
    }
}
