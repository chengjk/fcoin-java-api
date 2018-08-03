package com.fcoin.api.client.domain.reqs;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * created by jacky. 2018/8/3 3:09 PM
 */
@Getter
@Setter
@Data
public class PlaceOrderRequest {

   private String symbol;
   private String price;
   private String type;
   private String side;
   private String amount;
}
