package com.fcoin.api.client.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * created by jacky. 2018/8/1 5:51 PM
 */
@Getter
@Setter
public class Ticker {
    private String type;
    private long seq;
    private List<BigDecimal> ticker;


    /**
     * 最新成交价,
     */
    private BigDecimal lastPrice;
    /**
     * 最近一笔成交的成交量,
     */
    private BigDecimal lastVol;
    /**
     * 最大买一价,
     */
    private BigDecimal bestBidPrice;
    /**
     * 最大买一量,
     */
    private BigDecimal bestBidVol;
    /**
     * 最小卖一价,
     */
    private BigDecimal bestAskPrice;
    /**
     * 最小卖一量,
     */
    private BigDecimal bestAskVol;
    /**
     * 24小时前成交价,
     */
    private BigDecimal open24H;
    /**
     * 24小时内最高价,
     */
    private BigDecimal high24H;
    /**
     * 24小时内最低价,
     */
    private BigDecimal low24H;
    /**
     * 24小时内基准货币成交量, 如 btcusdt 中 btc 的量,
     */
    private BigDecimal vol24HBaseCurrency;
    /**
     * 24小时内计价货币成交量, 如 btcusdt 中 usdt 的量
     */
    private BigDecimal vol24HQuoteCurrency;


    public void setTicker(List<BigDecimal> va) {
        this.ticker = va;
        lastPrice = ticker.get(0);
        lastVol = ticker.get(1);
        bestBidPrice = ticker.get(2);
        bestBidVol = ticker.get(3);
        bestAskPrice = ticker.get(4);
        bestAskVol = ticker.get(5);
        open24H = ticker.get(6);
        high24H = ticker.get(7);
        low24H = ticker.get(8);
        vol24HBaseCurrency = ticker.get(9);
        vol24HQuoteCurrency = ticker.get(10);
    }
}
