package com.mohsin.microservices.currency_conversion_service.controllers;

import com.mohsin.microservices.currency_conversion_service.bean.CurrencyConversion;
import com.mohsin.microservices.currency_conversion_service.proxy.CurrencyExchangeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@Configuration(proxyBeanMethods = false)
class RestTemplateConfiguration {

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}

@RestController
public class CurrencyConversionController {

    @Autowired
    private Environment environment;

    @Autowired
    private CurrencyExchangeProxy proxy;

    @Autowired
    private RestTemplate restTemplate;

    // http://localhost:8100/currency-conversion/from/USD/to/PKR/quantity/10
    // To run more instances of this app on other ports, create new configuration in Intellij and add following to VM option
    // -Dserver.port=81001

    @GetMapping("/currency-conversion/from/{fromCurrency}/to/{toCurrency}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(
            @PathVariable String fromCurrency,
            @PathVariable String toCurrency,
            @PathVariable BigDecimal quantity
    ) {
        // Invoking currency exchange service
        var uriVariables = new HashMap<String, String>();
        uriVariables.put("fromCurrency", fromCurrency);
        uriVariables.put("toCurrency", toCurrency);

        var response = restTemplate.getForEntity(
                "http://localhost:8000/currency-exchange/from/{fromCurrency}/to/{toCurrency}",
                CurrencyConversion.class,
                uriVariables
        );

        var currencyConversion = response.getBody();
        if (currencyConversion == null) {
            throw new RuntimeException("Unable to find data for " + fromCurrency + " to " + toCurrency);
        }
        currencyConversion.setQuantity(quantity);
        currencyConversion.setTotalCalculatedAmount(currencyConversion.getConversionMultiple().multiply(quantity));
        currencyConversion.setEnvironment(currencyConversion.getEnvironment() + " - RestTemplate");
        return currencyConversion;
    }

    @GetMapping("/currency-conversion-feign/from/{fromCurrency}/to/{toCurrency}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionViaFeign(
            @PathVariable String fromCurrency,
            @PathVariable String toCurrency,
            @PathVariable BigDecimal quantity
    ) {
        var currencyConversion = proxy.getExchangeValue(fromCurrency, toCurrency);
        if (currencyConversion == null) {
            throw new RuntimeException("Unable to find data for " + fromCurrency + " to " + toCurrency);
        }
        currencyConversion.setQuantity(quantity);
        currencyConversion.setTotalCalculatedAmount(currencyConversion.getConversionMultiple().multiply(quantity));
        currencyConversion.setEnvironment(currencyConversion.getEnvironment() + " - FeignClient/Proxy");
        return currencyConversion;
    }
}
