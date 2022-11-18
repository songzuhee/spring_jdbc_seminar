package com.umc.src.Review;

import com.umc.src.Review.Model.PostReviewReq;
import com.umc.src.Review.Model.PostReviewRes;
import com.umc.config.BaseException;
import com.umc.config.BaseResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.umc.config.BaseResponseStatus.REVIEW_EMPTY_CONTENT;
import static com.umc.config.BaseResponseStatus.REVIEW_EMPTY_RATING;

@Api
@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewProvider reviewProvider;

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
    @PostMapping("/{userIdx}/{orderIdx}/{storeIdx}")
    public BaseResponse<PostReviewRes> createReview(@PathVariable("userIdx")int userIdx, @PathVariable("orderIdx") int orderIdx,
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

            PostReviewRes postReviewRes = reviewService.createReview(userIdx, orderIdx, storeIdx, postReviewReq);
            return new BaseResponse<>(postReviewRes);
        }   catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
