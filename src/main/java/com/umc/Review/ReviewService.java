package com.umc.Review;

import com.umc.Review.Model.PostReviewReq;
import com.umc.Review.Model.PostReviewRes;
import com.umc.config.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class ReviewService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReviewDao reviewDao;
    // 리뷰 작성하기
    @Transactional
    public PostReviewRes createReivew(int userIdx, int storeIdx, PostReviewReq postReviewReq) throws BaseException{
        int reviewIdx = reviewDao.createReview(userIdx, storeIdx, postReviewReq);
        return new PostReviewRes(reviewIdx);
    }
}
