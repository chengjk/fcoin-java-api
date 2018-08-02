package com.fcoin.api.client.domain.enums;

/**
 * created by jacky. 2018/7/21 2:24 PM
 * 行情深度.
 */

public enum DepthLevel {
    /**
     * 20档行情深度.
     */
    L20,
    /**
     * 100档行情深度.
     */
    L100,
    /**
     * 全量的行情深度, 不做时间保证和推送保证.
     */
    full
}
