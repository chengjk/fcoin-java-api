package com.fcoin.api.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/7/21 2:59 PM
 */
@Setter
@Getter
public class Symbol {
    private String name;
    @JsonProperty("base_currency")
    private String baseCurrency;
    @JsonProperty("quote_currency")
    private String quoteCurrency;
    @JsonProperty("price_decimal")
    private String priceDecimal;
    @JsonProperty("amount_decimal")
    private String amountDecimal;
}
