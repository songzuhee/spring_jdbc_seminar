package com.umc.src.order;

import com.umc.src.order.Model.GetUserRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class OrderDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 해당 유저 주문 상세 내역 조회
    public GetUserRes selectUserOlder(int userIdx) {
        String selectUserOlderQuery = "select o.userIdx, o.orderIdx, s.name, s.food_category, o.createdAt, o.pay_method, o.total_price\n" +
                "from `Order` o\n" +
                "left Join Store s\n" +
                "on s.storeIdx = o.storeIdx\n" +
                "where o.userIdx =?;";
        int selectUserOlderParams = userIdx;

        return this.jdbcTemplate.queryForObject(selectUserOlderQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getInt("orderIdx"),
                        rs.getString("name"),
                        rs.getString("food_category"),
                        rs.getString("createdAt"),
                        rs.getString("pay_method"),
                        rs.getInt("total_price")
                ), selectUserOlderParams);
    }
}
