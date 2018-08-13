package com.fcoin.api.client.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * created by jacky. 2018/7/21 2:28 PM
 */
@Getter
@Setter
public class Depth {
    private String type;
    private String seq;
    private long ts;
    //[price,vol,price1,vol1]
    private List<BigDecimal> bids;
    private List<BigDecimal> asks;
}
