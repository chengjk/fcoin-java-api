package com.fcoin.api.client;

import com.fcoin.api.client.constant.Consts;
import com.fcoin.api.client.domain.resp.RespBody;
import com.fcoin.api.client.exception.ApiException;
import com.fcoin.api.client.security.AuthenticationInterceptor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.StringUtils;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

/**
 * created by jacky. 2018/7/20 9:07 PM
 */
@Slf4j
public class FcoinApiServiceGenerator {


    static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(Consts.API_URL)
                    .addConverterFactory(JacksonConverterFactory.create());


    private static Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null, null);
    }

    public static <S> S createService(Class<S> serviceClass, String apiKey, String secret) {
        if (!StringUtils.isEmpty(apiKey) && !StringUtils.isEmpty(secret)) {
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(apiKey, secret);
            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);
                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }
        return retrofit.create(serviceClass);
    }

    /**
     * Execute a REST call and block until the response is received.
     */
    public static <T> T executeSync(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                parseBody(response.body());
                return response.body();
            } else {
                parseError(response);
            }
        } catch (IOException | ApiException e) {
            log.error(e.getMessage(), e);
        }
        throw new IllegalStateException("invalid response from server.");
    }

    private static <T> void parseBody(T body) throws ApiException {
        if (body instanceof RespBody) {
            RespBody resp = (RespBody) body;
            if (!resp.getStatus().equalsIgnoreCase("0")) {
                throw new ApiException(resp.toErrorString());
            }
        }
    }

    /**
     * Extracts and converts the response error body into an object.
     */
    public static ApiException parseError(Response<?> response) throws ApiException {
        throw new ApiException(response.raw().code(), response.raw().message());
    }


}
