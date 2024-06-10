package com.mohsin.microservices.currency_exchange_service.repositories;

import com.mohsin.microservices.currency_exchange_service.bean.CurrencyExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {

    CurrencyExchange findByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency);
}
