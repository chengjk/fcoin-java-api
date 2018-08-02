package com.fcoin.api.client.impl;

import com.fcoin.api.client.FcoinApiRestClient;
import com.fcoin.api.client.domain.*;
import com.fcoin.api.client.domain.enums.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;
import java.util.Set;

/**
 * created by jacky. 2018/7/21 10:48 AM
 */
@Slf4j
public class FcoinApiClientImplTest {


    private String apiKey = "a";
    private String apiSecret = "s";
    private FcoinApiRestClient client;

    @Before
    public void config() throws IOException {
        InputStream is = ClassLoader.getSystemResourceAsStream("config.properties");
        Properties props = new Properties();
        props.load(is);
        apiKey = props.getProperty("apiKey");
        apiSecret = props.getProperty("apiSecret");
        client = new FcoinApiRestClientImpl(apiKey, apiSecret);
    }

    @Test
    public void serverTime() {
        long timestamp = client.serverTime();
        log.info(String.valueOf(timestamp));
        assert timestamp > 0;
    }

    @Test
    public void currencies() {
        Set<String> currencies = client.currencies();
        log.info(currencies.toString());
        assert currencies != null;
    }

    @Test
    public void symbols() {
        Set<Symbol> symbols = client.symbols();
        assert symbols != null;
    }


    @Test
    public void ticker() {
        Ticker ticker = client.ticker("btcusdt");
        assert ticker != null;
    }

    @Test
    public void depth() {
        Depth result = client.depth(DepthLevel.L20, "btcusdt");
        assert result != null;
    }

    @Test
    public void trades() {
        Set<Trade> trades = client.trades("btcusdt", null, 5);
        assert trades != null;
    }

    @Test
    public void candles() {
        Set<Candle> candles = client.candles(Resolution.M1, "btcusdt", null, null);
        assert candles != null;
    }

    @Test
    public void balance() {
        Set<Asset> balance = client.balance();
        assert balance != null;
    }
    @Test
    public void place() {
        String id = client.place("btcusdt",OrderSide.buy,OrderType.limit,BigDecimal.ONE,BigDecimal.ONE);
        assert id != null;
    }
    @Test
    public void orders() {
        Set<Order> orders = client.orders("BTCUSDT", OrderState.SUBMITTED, null, null, null);
        assert orders != null;
    }
    @Test
    public void get() {
        Order order = client.get("aaa");
        assert order != null;
    }
    @Test
    public void cancel() {
        Boolean result = client.cancel("aaa");
        assert result != null;
    }
    @Test
    public void matchResult() {
        Set<MatchResult> results = client.matchResult("aaaa");
        assert results != null;
    }





}