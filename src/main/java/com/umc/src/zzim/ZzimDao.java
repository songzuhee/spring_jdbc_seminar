package com.umc.src.zzim;

import com.umc.src.zzim.Model.GetZzimListRes;
import com.umc.src.zzim.Model.GetZzimRes;
import com.umc.src.zzim.Model.PostZzimRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ZzimDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    List<GetZzimListRes> getZzimListRes;

    // 찜 생성
    public int createZzim(int userIdx, int storeIdx) {
        String createZzimQuery = "INSERT INTO Zzim (userIdx, storeIdx)" +
                "values (?,?);";
        Object[] createZzimParams = new Object[] {userIdx, storeIdx};
        this.jdbcTemplate.update(createZzimQuery, createZzimParams);

        String lastzzimIdxQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastzzimIdxQuery, int.class);
    }

    // 찜 리스트 조회
    public List<GetZzimRes> selectZzimList(int userIdx) {
        String selectZzimCountQuery = "SELECT count(zzimIdx) as ZzimCount FROM Zzim\n" +
                "WHERE userIdx = ? and status = 'ACTIVE';";
        int selectUserZzimParam = userIdx;

        return this.jdbcTemplate.query(selectZzimCountQuery,
                (rs, rowNum) -> new GetZzimRes(
                        rs.getInt("ZzimCount"),

                        getZzimListRes = this.jdbcTemplate.query(
                                "SELECT z.zzimIdx, s.name AS storeName, s.min_delivery_price,\n" +
                                        "       s.delivery_tip, s.store_img, s.delivery_time,\n" +
                                        "       AVG(r.rating) AS avgrating,\n" +
                                        "       count(r.rating) AS reviewCount, m.name as menuName\n" +
                                        "FROM Store AS s\n" +
                                        "    LEFT JOIN Review AS r ON r.storeIdx = s.storeIdx\n" +
                                        "    LEFT JOIN Menu AS m ON m.storeIdx = s.storeIdx\n" +
                                        "    LEFT JOIN Zzim AS z ON z.storeIdx = s.storeIdx\n" +
                                        "WHERE z.userIdx = ? AND z.status ='ACTIVE' AND s.status = 'ACTIVE'\n" +
                                        "GROUP BY s.storeIdx;",
                                (rk, rownum) -> new GetZzimListRes(
                                        rk.getInt("zzimIdx"),
                                        rk.getString("storeName"),
                                        rk.getInt("min_delivery_price"),
                                        rk.getInt("delivery_tip"),
                                        rk.getString("store_img"),
                                        rk.getInt("delivery_time"),
                                        rk.getInt("Avgrating"),
                                        rk.getInt("reviewCount"),
                                        rk.getString("menuName")
                                ), selectUserZzimParam
                        )
                ),selectUserZzimParam);

    }

    // 찜 취소
    public int updateZzimStatus(int userIdx, int storeIdx) {
        String deleteZzimQuery = "UPDATE Zzim SET status = 'INACTIVE' " +
                "WHERE userIdx = ? AND storeIdx =? ;";
        Object[] deleteZzimParam = new Object[]{userIdx, storeIdx};

        return this.jdbcTemplate.update(deleteZzimQuery, deleteZzimParam);
    }
}
