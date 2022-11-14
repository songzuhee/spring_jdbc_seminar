package com.umc.src.order.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserListRes {

    private int userIdx;
    private String name; // 가게 이름
    private String store_img; // 가게 프로필이미지
    private String food_name; // 주문한 메뉴
    private String status; // 주문 상태
}
