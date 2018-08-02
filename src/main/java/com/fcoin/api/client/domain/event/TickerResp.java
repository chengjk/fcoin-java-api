package com.fcoin.api.client.domain.event;

import com.fcoin.api.client.domain.Ticker;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * created by jacky. 2018/8/1 5:49 PM
 */
@Getter
@Setter
public class TickerResp extends Ticker {
    private String id;
    private Set<String> topics;
    private String status;
    private String msg;

}
