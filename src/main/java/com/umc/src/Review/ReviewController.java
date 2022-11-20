package com.umc.src.Review;

import com.umc.src.Review.Model.GetUserReviewListRes;
import com.umc.src.Review.Model.PostReviewReq;
import com.umc.src.Review.Model.PostReviewRes;
import com.umc.config.BaseException;
import com.umc.config.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @ApiOperation(value = "리뷰 작성 API", notes = "userIdx, orderIdx, storeIdx 입력받아 각각의 해당하는 값의 리뷰를 작성")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청 성공"),
            @ApiResponse(code = 4000, message = "서버 에러"),
            @ApiResponse(code = 5009, message = "별점을 입력하세요."),
            @ApiResponse(code = 5010, message = "리뷰 내용을 입력하세요.")
    })
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

    /*
     * 작성한 리뷰 리스트 조회
     * [GET] /reviews/:userIdx
     */
    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<List<GetUserReviewListRes>> getUserReivews(@PathVariable("userIdx")int userIdx){
        try {
            List<GetUserReviewListRes> getUserReviewListRes = reviewProvider.retrieveUserReviews(userIdx);
            return new BaseResponse<>(getUserReviewListRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
