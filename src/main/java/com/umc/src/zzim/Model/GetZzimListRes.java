package com.umc.src.zzim.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetZzimListRes {
    private int zzimIdx;
    private String storeName; // 가게 이름
    private int min_delivery_price; // 최소 주문 금액
    private int delivery_tip; // 배달팁
    private String store_img;
    private int delivery_time; // 배달 시간
    private int Avgrating; // 별점 평균
    private int reviewCount; // 리뷰 개수
    private String menuName; // 메뉴 이름 두개만!
}
