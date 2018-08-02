package com.fcoin.api.client.impl;

import com.fcoin.api.client.domain.enums.DepthLevel;
import com.fcoin.api.client.domain.enums.Resolution;
import org.junit.After;
import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;

/**
 * created by jacky. 2018/7/24 8:30 PM
 */
public class FcoinApiWebSocketClientImplTest {
    private FcoinApiWebSocketClientImpl ws = new FcoinApiWebSocketClientImpl();
    Closeable stream;

    @Test
    public void onTicker() {
        stream = ws.onTicker("BTCUSDT", data -> {
            if (!data.getType().equalsIgnoreCase("topics")) {
                System.out.println(data.getLastPrice());
            }
        });

    }
    @Test
    public void onDepth() {
        stream = ws.onDepth(DepthLevel.L20,"BTCUSDT", data -> {
            if (!data.getType().equalsIgnoreCase("topics")) {
                System.out.println(data.getTs());
            }
        });

    }


    @Test
    public void onTrade() {
        stream = ws.onTrade("BTCUSDT", data -> {
            System.out.println(data.getPrice());
        });

    }
    @Test
    public void onCandle() {
        stream = ws.onCandle(Resolution.M1,"BTCUSDT", data -> {
            System.out.println(data.getClose());
        });

    }


    @After
    public void after() throws InterruptedException, IOException {
        for (int i = 0; i < 5; i++) {
            Thread.sleep(1000L);
        }
        stream.close();
    }
}