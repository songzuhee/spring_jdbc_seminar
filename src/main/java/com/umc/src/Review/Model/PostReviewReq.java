package com.umc.src.Review.Model;

import lombok.*;

@Data
@NoArgsConstructor
public class PostReviewReq {
    private int userIdx;
    private int storeIdx;
    private int menuIdx;
    private int orderIdx;
    private int rating;
    private String review_img;
    private String content;

}
