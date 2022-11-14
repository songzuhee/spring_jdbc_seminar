package com.umc.src.order.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostUserReq {

    private int userIdx;
    private int optionIdx;
    private int storeIdx;
    private int menuIdx;
    private String pay_method;
    private int total_price;
    private String requests;

}
