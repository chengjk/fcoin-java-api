package com.fcoin.api.client.constant;

/**
 * created by jacky. 2018/7/20 8:56 PM
 */
public interface Consts {
    String API_HOST = "api.fcoin.com";
    String API_URL = "https://" + API_HOST;
    String WS_API_BASE_URL_PRO = "wss://api.fcoin.com/v2/ws";

    String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36";

    String ENDPOINT_SECURITY_TYPE_SIGNED = "FC-ACCESS-SIGNATURE";
    String ENDPOINT_SECURITY_TYPE_SIGNED_HEADER = ENDPOINT_SECURITY_TYPE_SIGNED + ":#";

}
