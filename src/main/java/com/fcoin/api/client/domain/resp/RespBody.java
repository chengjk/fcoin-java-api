package com.fcoin.api.client.domain.resp;

import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/7/21 11:16 AM
 */
@Getter
@Setter
public class RespBody<T> {
    private String status;
    private long ts;
    private String ch;
    private T data;
    private T tick;
    private String msg;


    public String toErrorString() {
        String format = "errMsg:%s.";
        return String.format(format, msg);
    }


}
