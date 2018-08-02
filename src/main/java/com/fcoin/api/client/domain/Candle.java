package com.fcoin.api.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * created by jacky. 2018/7/20 8:52 PM
 */
@Getter
@Setter
public class Candle {
    private long id;
    private String seq;
    private BigDecimal open;
    private BigDecimal close;
    private BigDecimal low;
    private BigDecimal high;
    private BigDecimal count;

    @JsonProperty("base_vol")
    private BigDecimal baseVol;
    @JsonProperty("quote_vol")
    private BigDecimal quoteVol;

}
