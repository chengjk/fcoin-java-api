package com.fcoin.api.client;

import com.fcoin.api.client.domain.*;
import com.fcoin.api.client.domain.enums.*;

import java.math.BigDecimal;
import java.util.Set;

/**
 * created by jacky. 2018/7/20 9:03 PM
 */
public interface FcoinApiRestClient {
    Long serverTime();

    Set<String> currencies();

    Set<Symbol> symbols();

    Ticker ticker(String symbol);

    Depth depth(DepthLevel level, String symbol);

    Set<Trade> trades(String symbol, String before, Integer limit);

    Set<Candle> candles(Resolution resolution, String symbol, String before, Integer limit);

    Set<Asset> balance();

    String place(String symbol, OrderSide side, OrderType type, BigDecimal price, BigDecimal amount);

    Set<Order> orders(String symbol, OrderState state, String before, String after, Integer limit);

    Order get(String orderId);

    Boolean cancel(String orderId);

    Set<MatchResult> matchResult(String orderId);
}
