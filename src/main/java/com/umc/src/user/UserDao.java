package com.umc.src.user;

import com.umc.src.user.Model.GetUserRes;
import com.umc.src.user.Model.PostJoinReq;
import com.umc.src.user.Model.PostJoinRes;
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

    // 회원가입
    public int createUser(PostJoinReq postJoinReq) {
        String createUserQuery = "insert into User(id, password, nickName, phoneNumber, email) values (?,?,?,?,?)";
        Object[] createUserParams = new Object[]{postJoinReq.getId(), postJoinReq.getPassword(),
                postJoinReq.getNickName(), postJoinReq.getPhoneNumber(), postJoinReq.getEmail()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lasrInsertQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lasrInsertQuery, int.class);
    }

    // 이메일 중복 검사
    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from User where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);
    }
}
