package com.fcoin.api.client.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * created by jacky. 2018/7/23 3:28 PM
 */
@Getter
@Setter
public class Asset {
    private String currency;
    private BigDecimal balance;
    private BigDecimal available;
    private BigDecimal frozen;

}
