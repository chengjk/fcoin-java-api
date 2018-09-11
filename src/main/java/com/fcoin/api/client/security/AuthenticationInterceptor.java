package com.fcoin.api.client.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fcoin.api.client.constant.Consts;
import okhttp3.*;
import okio.Buffer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

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
            String signature = createSignature(original, now);
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
     * @param request
     * @param ts
     * @return
     */
    private String createSignature(Request request, Long ts) {
        StringBuilder sb = new StringBuilder(1024);
        String method = request.method().toUpperCase();
        sb.append(method) // GET
                .append(Consts.API_URL.toLowerCase()) // Host
                .append(request.url().uri().getPath()); //path

        StringJoiner joiner = new StringJoiner("&");
        if (method.equalsIgnoreCase("GET")) {
            buildForGet(request, ts, sb, joiner);

        } else if (method.equalsIgnoreCase("POST")) {
            if (request.body() instanceof FormBody) {
                buildForPostFormBody(request, ts, sb, joiner);
            } else if (request.body() instanceof RequestBody) {
                buildForPostRequestBody(request, ts, sb, joiner);
            }
        }

        return HmacSHA1Signer.sign(sb.toString(), secret);
    }

    private void buildForPostRequestBody(Request request, Long ts, StringBuilder sb, StringJoiner joiner) {
        String bodyStr = bodyToString(request.body());
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<TreeMap<String, Object>> typeRef = new TypeReference<TreeMap<String, Object>>() {
        };
        try {
            sb.append(ts.toString());
            if (StringUtils.isNotBlank(bodyStr)) {
                //排序
                TreeMap<String, Object> params = mapper.readValue(bodyStr, typeRef);
                //拼接
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    joiner.add(entry.getKey() + '=' + entry.getValue());
                }
                sb.append(joiner.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildForPostFormBody(Request request, Long ts, StringBuilder sb, StringJoiner joiner) {
        FormBody body = (FormBody) request.body();
        TreeMap<String, Object> bodyParm = new TreeMap<>();
        //排序
        for (int i = 0; i < body.size(); i++) {
            bodyParm.put(body.encodedName(i), body.encodedValue(i));
        }
        //拼接
        for (Map.Entry<String, Object> entry : bodyParm.entrySet()) {
            joiner.add(entry.getKey() + '=' + entry.getValue());
        }
        sb.append(ts.toString()).append(joiner.toString());
    }

    private void buildForGet(Request request, Long ts, StringBuilder sb, StringJoiner joiner) {
        //参数排序
        TreeSet<String> names = new TreeSet(request.url().queryParameterNames());
        //拼接
        for (String key : names) {
            String value = request.url().queryParameter(key);
            joiner.add(key + '=' + urlEncode(value));
        }
        //追加问号
        if (!StringUtils.isEmpty(joiner.toString())) {
            sb.append("?");
        }
        sb.append(joiner.toString()).append(ts.toString());
    }


    private String bodyToString(final RequestBody body) {
        try {
            final RequestBody copy = body;
            final Buffer buffer = new Buffer();
            if (copy != null) {
                copy.writeTo(buffer);
            } else {
                return "";
            }
            return buffer.readUtf8();

        } catch (IOException e) {
            return null;
        }
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