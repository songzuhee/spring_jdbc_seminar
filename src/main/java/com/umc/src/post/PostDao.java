package com.umc.src.post;

import com.umc.src.post.Model.GetPostRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class PostDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 게시글 조회
    public GetPostRes selectPost(int postIdx) {
        String selectPostQuery = "select * from Post where postIdx = ?;";
        int selectPostParams = postIdx;

        return this.jdbcTemplate.queryForObject(selectPostQuery,
                (rs, rowNum) -> new GetPostRes(
                        rs.getInt("postIdx"),
                        rs.getString("nickName"),
                        rs.getString("imgUrl"),
                        rs.getString("content")), selectPostParams);
    }
}
