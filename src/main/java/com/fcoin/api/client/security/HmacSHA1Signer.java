package com.fcoin.api.client.security;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * created by jacky. 2018/8/1 3:04 PM
 */
public class HmacSHA1Signer {
    public static String sign(String message, String secret) {
        try {
            message = Base64.getEncoder().encodeToString(message.getBytes());
            Mac hmacSHA1 = Mac.getInstance("HmacSHA1");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA1");
            hmacSHA1.init(secretKeySpec);
            byte[] hash = hmacSHA1.doFinal(message.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Unable to sign message.", e);
        }
    }


    public static void main(String[] args) {
        String sign = HmacSHA1Signer.sign("POSThttps://api.fcoin.com/v2/orders1523069544359amount=100.0&price=100.0&side=buy&symbol=btcusdt&type=limit", "3600d0a74aa3410fb3b1996cca2419c8");
        assert sign.equals("DeP6oftldIrys06uq3B7Lkh3a0U=");
        System.out.println(sign);
    }
}
