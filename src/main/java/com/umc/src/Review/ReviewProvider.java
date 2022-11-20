package com.umc.src.Review;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.umc.config.BaseException;
import com.umc.src.Review.Model.GetUserReviewListRes;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.umc.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class ReviewProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReviewDao reviewDao;

    // 유저가 작성한 리뷰 리스트 조회
    public List<GetUserReviewListRes> retrieveUserReviews(int userIdx) throws BaseException{
        try  {
            List<GetUserReviewListRes> getUserReviewListRes = reviewDao.selectUserReviews(userIdx);
            return getUserReviewListRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
