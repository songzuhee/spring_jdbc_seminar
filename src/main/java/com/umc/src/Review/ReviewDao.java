package com.umc.src.Review;


import com.umc.src.Review.Model.GetUserOrderMenuRes;
import com.umc.src.Review.Model.GetUserReviewListRes;
import com.umc.src.Review.Model.PostReviewReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;


@Repository
public class ReviewDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){ this.jdbcTemplate = new JdbcTemplate(dataSource); }

    List<GetUserOrderMenuRes> getUserOrderMenuRes;
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

    // 유저가 작성한 리뷰 조회
    public List<GetUserReviewListRes> selectUserReviews(int userIdx) {
        String selectUserReviewsQuery = "select r.reviewIdx, s.storeIdx, s.name as storeName, r.rating,\n" +
                "       r.content, r.review_img as imgUrl\n" +
                "from Review as r\n" +
                "left join Store as s on s.storeIdx = r.storeIdx\n" +
                "where userIdx = ? and r.status = 'ACTIVE' and s.status = 'ACTIVE';";
        int selectUserReivewsParam = userIdx;

        return this.jdbcTemplate.query(selectUserReviewsQuery,
                (rs, rowNum) -> new GetUserReviewListRes(
                        rs.getInt("reviewIdx"),
                        rs.getInt("storeIdx"),
                        rs.getString("storeName"),
                        rs.getInt("rating"),
                        rs.getString("content"),
                        rs.getString("imgUrl"),


                        getUserOrderMenuRes = this.jdbcTemplate.query(
                                "select m.name as menu from Menu as m\n" +
                                        "left join `Order` as o on o.menuIdx = m.menuIdx\n" +
                                        "left join Store as s on s.storeIdx = m.storeIdx\n" +
                                        "where o.userIdx = ?;",
                                (rk, rownum) -> new GetUserOrderMenuRes(
                                        rk.getString("menu")
                                ), selectUserReivewsParam
                        )
                ),selectUserReivewsParam);
    }
}
