package com.umc.src.Point.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetPointRes {
    private int totalPoint; // 포인트 총금액
    private List<GetPointListRes> getPointListRes;
}
