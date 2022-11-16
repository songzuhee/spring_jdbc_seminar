package com.umc.Review;

import com.umc.Review.Model.PostReviewReq;
import com.umc.Review.Model.PostReviewRes;
import com.umc.config.BaseException;
import com.umc.config.BaseResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private ReviewService reviewService;
    private ReviewProvider reviewProvider;

    @Autowired
    public ReviewController(ReviewProvider reviewProvider, ReviewService reviewService) {
        this.reviewProvider = reviewProvider;
        this.reviewService = reviewService;
    }

    /*
     * 리뷰 작성 API
     * [POST] /reviews/
     */
    @ResponseBody
    @PostMapping("/:userIdx/:storeIdx")
    public BaseResponse<PostReviewReq> createReview(@PathVariable("userIdx")int userIdx,
           @PathVariable("storeIdx") int storeIdx, @RequestBody PostReviewReq postReviewReq) throws BaseException {
        try {
            if (postReviewReq.getRating() == 0)
            {
                return new BaseResponse<>(REVIEW_EMPTY_RATING);
            }
            if (postReviewReq.getContent() == null)
            {
                return new BaseResponse<>(REVIEW_EMPTY_CONTENT);
            }

            PostReviewRes postReviewRes = reviewService.createReview(userIdx, storeIdx, postReviewReq);
            return new BaseResponse<>(postReviewReq);
        }   catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
