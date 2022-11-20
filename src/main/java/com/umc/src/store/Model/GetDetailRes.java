package com.umc.src.store.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetDetailRes {
    private int storeIdx;
    private String storeName;
    private int Avgrating; //별점 평균
    private int ownerComment; //사장님 리뷰 개수
    private int ZzimCount; // 찜 개수
    private int min_delivery_price;
    private int delivery_time; // 배달 시간
    private int delivery_tip;
    private String operatingTime; // 운영 시간
    private String phoneNumber;
    private String owner;
    private String delivery_address; // 배달 지역
    private String address; // 가게 주소
    private List<GetStoreMenuRes> getStoreMenuResList;


}
