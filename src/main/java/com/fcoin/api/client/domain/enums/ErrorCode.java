package com.fcoin.api.client.domain.enums;

import com.fcoin.api.client.exception.ApiException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * created by jacky. 2018/8/1 8:44 PM
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    BadRequest(400, "错误的请求"),
    Unauthorized(401, "API key 或者签名，时间戳有误"),
    Forbidden(403, "禁止访问"),
    NotFound(404, "未找到请求的资源"),
    MethodNotAllowed(405, "使用的 HTTP 方法不适用于请求的资源"),
    NotAcceptable(406, "请求的内容格式不是 JSON"),
    ooManyRequests(429, "请求受限，请降低请求频率"),
    InternalServerError(500, "服务内部错误，请稍后再进行尝试"),
    ServiceUnavailable(503, "服务不可用，请稍后再进行尝试");
    private int code;
    private String desc;

    public ErrorCode fromCode(int code) {
        return Arrays.stream(ErrorCode.values())
                .filter(f -> f.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new ApiException("unknown type"));
    }
}
