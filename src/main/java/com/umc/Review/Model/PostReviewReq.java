package com.umc.Review.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostReviewReq {
    private int userIdx;
    private int storeIdx;
    private int rating;
    private String review_img;
    private String menu_name;
    private String content;

}
