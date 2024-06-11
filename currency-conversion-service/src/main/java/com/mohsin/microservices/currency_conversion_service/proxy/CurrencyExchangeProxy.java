package com.mohsin.microservices.currency_conversion_service.proxy;

import com.mohsin.microservices.currency_conversion_service.bean.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Name: Typically the service name we want to call
@FeignClient(name = "currency-exchange-service")
public interface CurrencyExchangeProxy {

    @GetMapping("/currency-exchange/from/{fromCurrency}/to/{toCurrency}")
    public CurrencyConversion getExchangeValue(
            @PathVariable String fromCurrency,
            @PathVariable String toCurrency
    );
}
