package com.fcoin.api.client;

import com.fcoin.api.client.constant.Consts;
import com.fcoin.api.client.domain.*;
import com.fcoin.api.client.domain.resp.RespBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Set;

/**
 * created by jacky. 2018/7/20 8:41 PM
 */
public interface FcoinApiService {

    @GET("v2/public/server-time")
    Call<RespBody<Long>> serverTime();

    @GET("v2/public/currencies")
    Call<RespBody<Set<String>>> currencies();

    @GET("v2/public/symbols")
    Call<RespBody<Set<Symbol>>> symbols();


    @GET("v2/market/ticker/{symbol}")
    Call<RespBody<Ticker>> ticker(@Path("symbol") String symbol);

    @GET("v2/market/depth/{level}/{symbol}")
    Call<RespBody<Depth>> depth(@Path("level") String level, @Path("symbol") String symbol);

    @GET("v2/market/trades/{symbol}")
    Call<RespBody<Set<Trade>>> trades(@Path("symbol") String symbol, @Query("before") String before, @Query("limit") Integer limit);

    @GET("v2/market/candles/{resolution}/{symbol}")
    Call<RespBody<Set<Candle>>> candles(@Path("resolution") String resolution, @Path("symbol") String symbol, @Query("before") String before, @Query("limit") Integer limit);


    @Headers(Consts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("v2/accounts/balance")
    Call<RespBody<Set<Asset>>> balance();


    @Headers(Consts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("v2/orders")
    Call<RespBody<String>> place(@Query("symbol") String symbol, @Query("side") String side,
                                 @Query("type") String type, @Query("price") String price,
                                 @Query("amount") String amount);


    @Headers(Consts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("v2/orders")
    Call<RespBody<Set<Order>>> orders(@Query("symbol") String symbol, @Query("state") String state,
                                      @Query("before") String before, @Query("after") String after,
                                      @Query("limit") String limit);

    @Headers(Consts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("v2/orders/{order_id}")
    Call<RespBody<Order>> get(@Path("order_id") String orderId);


    @Headers(Consts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @POST("v2/orders/{order_id}/submit-cancel")
    Call<RespBody<Boolean>> cancel(@Path("order_id") String orderId);


    @Headers(Consts.ENDPOINT_SECURITY_TYPE_SIGNED_HEADER)
    @GET("v2/orders/{order_id}/match-results")
    Call<RespBody<Set<MatchResult>>> matchResult(@Path("order_id") String orderId);


}
