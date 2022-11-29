package com.umc.src.Point.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetPointListRes {
    private int pointIdx;
    private String storeName; // 가게이름
    private String createdAt; // 포인트 생성날짜
    private String expiredAt; // 포인트 만료일자
    private int point; // 해당 포인트
}
