package com.fcoin.api.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fcoin.api.client.domain.enums.OrderSide;
import com.fcoin.api.client.domain.enums.OrderType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * created by jacky. 2018/7/26 11:01 AM
 */
@Getter
@Setter
public class MatchResult {
    private BigDecimal price;
    @JsonProperty("filled_fees")
    private BigDecimal filledFees;
    @JsonProperty("filled_amount")
    private BigDecimal filledAmount;
    private OrderSide side;
    private OrderType type;
    @JsonProperty("created_at")
    private String createdAt;
}