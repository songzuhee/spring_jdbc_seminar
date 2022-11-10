package com.umc.src.auth;

import com.umc.src.auth.Model.PostLoginReq;
import com.umc.src.auth.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
@Repository
public class AuthDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 로그인
    public User getPassword(PostLoginReq postLoginReq) {
        String getPwdQuery = "select userIdx, id, password from User where id = ?";
        String getPwdParams = postLoginReq.getId();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs, rowNum) -> new User(
                        rs.getInt("userIdx"),
                        rs.getString("id"),
                        rs.getString("password")),
                getPwdParams
        );
    }

    // 유저 확인
    public String checkUserStatus(String id){
        String checkUserStatusQuery = "select status from User where id = ? ";
        String checkUserStatusParams = id;
        return this.jdbcTemplate.queryForObject(checkUserStatusQuery,
                String.class,
                checkUserStatusParams);

    }
}
