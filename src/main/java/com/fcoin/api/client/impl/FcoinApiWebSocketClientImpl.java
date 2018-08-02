package com.fcoin.api.client.impl;

import com.fcoin.api.client.FcoinApiWebSocketClient;
import com.fcoin.api.client.constant.Consts;
import com.fcoin.api.client.domain.enums.DepthLevel;
import com.fcoin.api.client.domain.enums.Resolution;
import com.fcoin.api.client.domain.event.CandleResp;
import com.fcoin.api.client.domain.event.DepthResp;
import com.fcoin.api.client.domain.event.TickerResp;
import com.fcoin.api.client.domain.event.TradeResp;
import com.fcoin.api.client.domain.resp.ApiCallback;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

import java.io.Closeable;

/**
 * created by jacky. 2018/7/24 4:00 PM
 */
public class FcoinApiWebSocketClientImpl implements FcoinApiWebSocketClient {

    private OkHttpClient client;


    public FcoinApiWebSocketClientImpl() {
        Dispatcher d = new Dispatcher();
        d.setMaxRequestsPerHost(100);
        this.client = new OkHttpClient.Builder().dispatcher(d).build();
    }


    @Override
    public Closeable onTicker(String symbol, ApiCallback<TickerResp> callback) {

        String topic = String.format("{\"cmd\":\"sub\",\"args\":[\"ticker.%s\"]}", symbol.toLowerCase());
        return createNewWebSocket(topic, new FcoinApiWebSocketListener<>(callback, TickerResp.class));
    }

    @Override
    public Closeable onDepth(DepthLevel level, String symbol, ApiCallback<DepthResp> callback) {
        String topic = String.format("{\"cmd\":\"sub\",\"args\":[\"depth.%s.%s\"]}", level.name(),symbol.toLowerCase());
        return createNewWebSocket(topic, new FcoinApiWebSocketListener<>(callback, DepthResp.class));
    }

    @Override
    public Closeable onCandle(Resolution resolution, String symbol, ApiCallback<CandleResp> callback) {
        String topic = String.format("{\"cmd\":\"sub\",\"args\":[\"candle.%s.%s\"]}", resolution.name(),symbol.toLowerCase());
        return createNewWebSocket(topic, new FcoinApiWebSocketListener<>(callback, CandleResp.class));
    }

    @Override
    public Closeable onTrade(String symbol, ApiCallback<TradeResp> callback) {
        String topic = String.format("{\"cmd\":\"sub\",\"args\":[\"trade.%s\"]}", symbol.toLowerCase());
        return createNewWebSocket(topic, new FcoinApiWebSocketListener<>(callback, TradeResp.class));
    }

    private Closeable createNewWebSocket(String sub, FcoinApiWebSocketListener<?> listener) {
        String streamingUrl = Consts.WS_API_BASE_URL_PRO;
        Request request = new Request.Builder().url(streamingUrl).build();
        final WebSocket webSocket = client.newWebSocket(request, listener);
        webSocket.send(sub);
        return () -> {
            final int code = 1000;
            listener.onClosing(webSocket, code, null);
            webSocket.close(code, null);
            listener.onClosed(webSocket, code, null);
        };
    }

}
