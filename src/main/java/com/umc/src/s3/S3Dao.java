package com.umc.src.s3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Repository
public class S3Dao {
    private JdbcTemplate jdbcTemplate;

    @Resource
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 유저 프로필 이미지 업로드
    public void uploadUserPicture(String imgPath, int userIdx) {
        String uploadUserPictureQuery = "update User set profile_img = ? where userIdx = ? ;";
        Object[] uploadUserPictureParams = new Object[]{imgPath, userIdx};
        this.jdbcTemplate.update(uploadUserPictureQuery, uploadUserPictureParams);
    }
}
