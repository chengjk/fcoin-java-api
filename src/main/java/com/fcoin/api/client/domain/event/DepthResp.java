package com.fcoin.api.client.domain.event;

import com.fcoin.api.client.domain.Depth;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * created by jacky. 2018/7/24 8:43 PM
 */
@Getter
@Setter
public class DepthResp  extends  Depth{
    private Set<String> topics;
    private String status;
    private String msg;
    private String type;

}
