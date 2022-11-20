package com.umc.src.store.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetCategoryListRes {
    private int storeIdx;
    private String storeName;
    private int Avgrating; //  별점 평균
    private int min_delivery_price;
    private int delivery_tip;
    private String store_img;
    private int reviewCount; // 리뷰 개수
}
