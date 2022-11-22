package com.umc.src.zzim;

import com.umc.src.zzim.Model.PostZzimRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class ZzimDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 찜 생성
    public int createZzim(int userIdx, int storeIdx) {
        String createZzimQuery = "INSERT INTO Zzim (userIdx, storeIdx)" +
                "values (?,?);";
        Object[] createZzimParams = new Object[] {userIdx, storeIdx};
        this.jdbcTemplate.update(createZzimQuery, createZzimParams);

        String lastzzimIdxQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastzzimIdxQuery, int.class);
    }
}
