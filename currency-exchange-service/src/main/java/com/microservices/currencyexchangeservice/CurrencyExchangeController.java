package com.microservices.currencyexchangeservice;

import com.fasterxml.jackson.databind.annotation.JsonAppend.Attr;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

    @Autowired
    private CurrencyExchangeRepository repository;
    @Autowired
    private Environment environment;

    @GetMapping("currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to){
//        var currency = new CurrencyExchange(1000L, from, to, BigDecimal.valueOf(50));
        var currency = repository.findByFromAndTo(from.toUpperCase() ,to.toUpperCase());

        if (currency == null)
            throw new RuntimeException("Unable to find data for " + from + " to " + to);

        currency.setEnvironment(
              environment.getProperty("local.server.port")
        );
        return currency;
    }
}
