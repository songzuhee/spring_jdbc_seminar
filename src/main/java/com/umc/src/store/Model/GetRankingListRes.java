package com.umc.src.store.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetRankingListRes {
    private int storeIdx;
    private String food_category; // 가게 카테고리
    private String name; // 가게 이름
    private int countRating; // 별점 갯수
    private int min_delivery_price; // 최소주문금액
    private int delivery_tip; // 배달팁
    private int delivery_time; // 배달시간
    private String store_img; // 가게 프로필 이미지
}
