package com.umc.src.Review;


import com.umc.src.Review.Model.PostReviewReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;


@Repository
public class ReviewDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){ this.jdbcTemplate = new JdbcTemplate(dataSource); }

    // 리뷰 작성하기
    public int createReview(int userIdx, int orderIdx, int storeIdx, PostReviewReq postReviewReq) {
        String createReviewQuery = "insert into Review (rating, review_img, content,\n" +
                "                                    userIdx, menuIdx, orderIdx, storeIdx) values\n" +
                "                               (?,?,?,?,?,?,?) ;";

        Object[] createReviewParams = new Object[]{postReviewReq.getRating(), postReviewReq.getReview_img(),
        postReviewReq.getContent(), userIdx, postReviewReq.getMenuIdx(), orderIdx, storeIdx};
        this.jdbcTemplate.update(createReviewQuery, createReviewParams);

        String lastReviewIdxQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastReviewIdxQuery, int.class);

    }
}
