package com.fcoin.api.client.security;

import com.fcoin.api.client.constant.Consts;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Instant;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.TreeSet;

/**
 * A request interceptor that injects the API Key into requests, and signs messages, whenever required.
 */
public class AuthenticationInterceptor implements Interceptor {
    private final String apiKey;
    private final String secret;

    public AuthenticationInterceptor(String apiKey, String secret) {
        this.apiKey = apiKey;
        this.secret = secret;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder newRequestBuilder = original.newBuilder();
        boolean isSignatureRequired = original.header(Consts.ENDPOINT_SECURITY_TYPE_SIGNED) != null;
        Long now = System.currentTimeMillis();


        newRequestBuilder.addHeader("User-Agent", Consts.USER_AGENT);
        if (original.method().equalsIgnoreCase("POST")) {
            newRequestBuilder.addHeader("Content-Type", "application/json");
        } else if (original.method().equalsIgnoreCase("GET")) {
            newRequestBuilder.addHeader("Content-Type", "application/x-www-form-urlencoded");
        }

        //add heads if required
        if (isSignatureRequired) {
            newRequestBuilder.removeHeader(Consts.ENDPOINT_SECURITY_TYPE_SIGNED);
            String signature = createSignature(original.method(), original.url(), now);
            newRequestBuilder.addHeader("FC-ACCESS-KEY", apiKey);
            newRequestBuilder.addHeader("FC-ACCESS-TIMESTAMP", now.toString());
            newRequestBuilder.addHeader("FC-ACCESS-SIGNATURE", signature);
        }
        // Build new request after adding the necessary authentication information
        Request newRequest = newRequestBuilder.build();
        return chain.proceed(newRequest);
    }


    /**
     * 创建签名.
     * https://developer.fcoin.com/zh.html?python#api
     *
     * @param method
     * @param request
     * @param ts
     * @return
     */
    private String createSignature(String method, HttpUrl request, Long ts) {
        StringBuilder sb = new StringBuilder(1024);
        sb.append(method.toUpperCase()) // GET
                .append(Consts.API_URL.toLowerCase()) // Host
                .append(request.uri().getPath()); //path

        StringJoiner joiner = new StringJoiner("&");
        //参数排序
        TreeSet<String> names = new TreeSet(request.queryParameterNames());
        //拼接
        for (String key : names) {
            String value = request.queryParameter(key);
            joiner.add(key + '=' + urlEncode(value));
        }
        if (method.equalsIgnoreCase("GET")) {
            if (!StringUtils.isEmpty(joiner.toString())) {
                sb.append("?");
            }
            sb.append(joiner.toString()).append(ts.toString());
        } else if (method.equalsIgnoreCase("POST")) {
            sb.append(ts.toString()).append(joiner.toString());
        }

        return HmacSHA1Signer.sign(sb.toString(), secret);
    }


    /**
     * 使用标准URL Encode编码。注意和JDK默认的不同，空格被编码为%20而不是+。
     *
     * @param s String字符串
     * @return URL编码后的字符串
     */
    private String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("UTF-8 encoding not supported!");
        }
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AuthenticationInterceptor that = (AuthenticationInterceptor) o;
        return Objects.equals(apiKey, that.apiKey) &&
                Objects.equals(secret, that.secret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apiKey, secret);
    }
}