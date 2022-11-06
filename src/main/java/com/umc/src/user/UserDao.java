package com.umc.src.user;

import com.umc.src.user.Model.GetUserRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 유저 조회
    public GetUserRes selectUser(int userIdx) {
        String selectUserQuery = "select * from User where userIdx = ?;";
        int selectUserParams = userIdx;

        return this.jdbcTemplate.queryForObject(selectUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("id"),
                        rs.getString("phoneNumber"),
                        rs.getString("nickName"),
                        rs.getString("grade"),
                        rs.getString("address"),
                        rs.getString("status"),
                        rs.getString("createdAt"),
                        rs.getString("email"),
                        rs.getString("profile_img")),selectUserParams);

    }
}
