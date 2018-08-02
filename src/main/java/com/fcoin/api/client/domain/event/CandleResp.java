package com.fcoin.api.client.domain.event;

import com.fcoin.api.client.domain.Candle;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * created by jacky. 2018/8/1 8:06 PM
 */
@Getter
@Setter
public class CandleResp extends Candle {
    private Set<String> topics;
    private String status;
    private String msg;
    private String seq;
    private String type;
}
