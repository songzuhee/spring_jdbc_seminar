package com.umc.src.Review;

import com.umc.src.Review.Model.PostReviewReq;
import com.umc.src.Review.Model.PostReviewRes;
import com.umc.config.BaseException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
public class ReviewService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReviewDao reviewDao;


    // 리뷰 작성하기
    @Transactional
    public PostReviewRes createReview(int userIdx, int orderIdx, int storeIdx, PostReviewReq postReviewReq) throws BaseException{
        int reviewIdx = reviewDao.createReview(userIdx, orderIdx, storeIdx, postReviewReq);
        return new PostReviewRes(reviewIdx);
    }
}
