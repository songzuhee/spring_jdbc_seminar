package com.umc.src.order.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserRes {


    private int userIdx;
    private int orderIdx;
    private String name; // 가게 이름
    private String food_category; //주문한 메뉴 이름
    private String createdAt; // 주문 날짜
    private String pat_method; // 결제 방법
    private int total_price; // 결제 총 금액

}
