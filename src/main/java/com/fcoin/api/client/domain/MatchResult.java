package com.fcoin.api.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fcoin.api.client.domain.enums.MatchResultType;
import com.fcoin.api.client.domain.enums.OrderSide;
import com.fcoin.api.client.domain.enums.OrderSource;
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
    @JsonProperty("fill_fees")
    private BigDecimal fillFees;
    @JsonProperty("filled_amount")
    private BigDecimal filledAmount;
    private OrderSide side;
    private MatchResultType type;
    @JsonProperty("created_at")
    private String createdAt;
    private OrderSource source;
}