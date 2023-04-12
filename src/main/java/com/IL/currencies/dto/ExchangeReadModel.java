package com.IL.currencies.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
public class ExchangeReadModel implements Serializable {
    private String baseCurrency;
    private String rateCurrency;
    private Float rateValue;


    @Override
    public String toString() {
        return "ExchangeReadModel{" +
                "baseCurrency='" + baseCurrency + '\'' +
                ", rateCurrency='" + rateCurrency + '\'' +
                ", rateValue=" + rateValue +
                '}';
    }
}
