package com.umc.src.user;

import com.umc.src.user.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

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

    // 전체 유저 조회
    public List<GetUserListRes> selectUserList() {
        String selectUserListQuery = "select * from User;";
        return this.jdbcTemplate.query(selectUserListQuery,
                (rs, rowNum) -> new GetUserListRes(
                        rs.getInt("userIdx"),
                        rs.getString("nickName")
                ));
    }

    // 회원 정보 수정
    public int updateProfile(int userIdx, PostUpdateReq postUpdateReq, int userIdxByJwt){
        String updateUserNameQuery = "update User set phoneNumber=?,nickName=?, email = ?, password=?where userIdx = ? ";
        Object[] updateUserNameParams = new Object[]{postUpdateReq.getPhoneNumber(), postUpdateReq.getNickName(),postUpdateReq.getEmail(), postUpdateReq.getPassword(), userIdx};

        return this.jdbcTemplate.update(updateUserNameQuery,updateUserNameParams);
    }
    // 이메일 중복 검사
    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from User where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);
    }

    // 회원 확인
    public int checkUserExist(int userIdx){
        String checkUserExistQuery = "select exists(select userIdx from User where userIdx = ?)";
        int checkUserExistParams = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserExistQuery,
                int.class,
                checkUserExistParams);

    }

    // 회원 삭제
    public int updateUserStatus(int userIdx) {
        String deleteUserQuery = "UPDATE User SET status = 'INACTIVE' WHERE userIdx =?;";
        Object[] deleteUserParams = new Object[]{userIdx};

        return this.jdbcTemplate.update(deleteUserQuery, deleteUserParams);
    }

    public int findByMemberEmail(String email) {
        String findEmailQuery = "select userIdx from User where email = ?;";
        String EmailParams = email;

        return this.jdbcTemplate.queryForObject(findEmailQuery, int.class, EmailParams);
    }

    public int updatePassword(int userIdx, String password) {

        String updatePasswordQuery = "update User set password=? where userIdx =?;";
        Object[] updatePasswordParams = new Object[] {password, userIdx};

        return this.jdbcTemplate.update(updatePasswordQuery, updatePasswordParams);
    }
}



