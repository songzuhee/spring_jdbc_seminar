package com.umc.src.post;

import com.umc.src.post.Model.GetPostRes;
import com.umc.src.post.Model.PostPostReq;
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
                        rs.getString("content")), selectPostParams);
    }

    // 게시글 생성
    public int createPost(PostPostReq postPostReq) {
        String createPostQuery = "insert into Post (content) values (?);";
        Object[] createPostParams = new Object[]{postPostReq.getContent()};

        this.jdbcTemplate.update(createPostQuery, createPostParams);

        String lastpostIdxQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastpostIdxQuery, int.class);
    }

    public int deletePost(int postIdx) {
        String deletePostQuery = "update Post SET status = 'DELETED' where = postIdx= ? ;";
        int deletePostParams = postIdx;
        return this.jdbcTemplate.update(deletePostQuery, deletePostParams);
    }

}
