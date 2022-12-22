package com.umc.src.Point;

import com.umc.src.Point.Model.GetPointListRes;
import com.umc.src.Point.Model.GetPointRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PointDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    List<GetPointListRes> getPointListRes;

    // 포인트 조회
    public List<GetPointRes> selectPointList(int userIdx) {
        String selectTotalPointQuery = "select sum(point) as totalPoint from Point where userIdx = ?;";
        int selectTotalPointParam = userIdx;

        return this.jdbcTemplate.query(selectTotalPointQuery,
                (rs, rowNum) -> new GetPointRes(
                        rs.getInt("totalPoint"),
                        getPointListRes = this.jdbcTemplate.query(
                                "select p.pointIdx as pointIdx, \n" +
                                        "       o.createdAt as createAt, p.expiredAt as expiredAt, p.point,\n" +
                                        "       s.name as storeName\n" +
                                        "from Point as p\n" +
                                        "      left join `Order` as o\n" +
                                        "      on o.orderIdx = p.orderIdx\n" +
                                        "      left join Store as s\n" +
                                        "      on s.storeIdx = p.storeIdx\n" +
                                        "where p.userIdx = ?\n" +
                                        "order by o.createdAt asc;",
                                (rk, rownum) -> new GetPointListRes(
                                        rk.getInt("pointIdx"),
                                        rk.getString("storeName"),
                                        rk.getString("createAt"),
                                        rk.getString("expiredAt"),
                                        rk.getInt("point")
                                ), selectTotalPointParam
                        )
                ),selectTotalPointParam);
    }
}
