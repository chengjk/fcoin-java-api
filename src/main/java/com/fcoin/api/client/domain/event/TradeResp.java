package com.fcoin.api.client.domain.event;

import com.fcoin.api.client.domain.Trade;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * created by jacky. 2018/8/1 7:38 PM
 */
@Getter
@Setter
public class TradeResp extends Trade {
    private String type;
    private Set<String> topics;
    private String status;
    private String msg;
}
