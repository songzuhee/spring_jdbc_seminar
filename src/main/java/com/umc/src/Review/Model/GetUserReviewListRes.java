package com.umc.src.Review.Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetUserReviewListRes {
    private int reviewIdx;
    private int storeIdx;
    private String storeName;
    private int rating;
    private String content;
    private String imgUrl;
    private List<GetUserOrderMenuRes> getUserOrderMenuRes;
}
