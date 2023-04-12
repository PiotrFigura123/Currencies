package com.IL.currencies.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ExchangeDto {

    private int transactionId;
    private int versionId;
    private int baseCurrency;
    private int rateCurrency;
    private String rateValue;

    public ExchangeDto() {
    }


}