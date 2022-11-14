package com.umc.src.order;

import com.umc.src.order.Model.GetUserListRes;
import com.umc.src.order.Model.GetUserRes;
import com.umc.src.order.Model.PostUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

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

    // 유저 주문내역 전체 조회
    public List<GetUserListRes> selectUserOrderList(int userIdx) {
        String selectUserOrderListQuery ="select o.userIdx, s.name, s.store_img, m.name as food_name, o.status\n" +
                "from `Order` o\n" +
                "         left Join Store as s\n" +
                "                   on s. storeIdx = o.storeIdx\n" +
                "         left Join Menu as m\n" +
                "                   on m.storeIdx = s.storeIdx\n" +
                "where o.userIdx =?;";
        int selectUserOrderListParams = userIdx;

        return this.jdbcTemplate.query(selectUserOrderListQuery,
                (rs, rowNum) -> new GetUserListRes(
                        rs.getInt("userIdx"),
                        rs.getString("name"),
                        rs.getString("store_img"),
                        rs.getString("food_name"),
                        rs.getString("status")
                ),selectUserOrderListParams);
    }

    // 주문 하기
    public int createOrder(int userIdx, PostUserReq postUserReq) {
        String createOrderQuery = "insert into `Order`(optionIdx, storeIdx, " +
                "menuIdx, pay_method, total_price, requests, userIdx) " +
                "values (?,?,?,?,?,?,?);";

        Object[] createOrderParams = new Object[]{postUserReq.getOptionIdx(), postUserReq.getStoreIdx(),
        postUserReq.getMenuIdx(), postUserReq.getPay_method(), postUserReq.getTotal_price(), postUserReq.getRequests(),userIdx};
        this.jdbcTemplate.update(createOrderQuery, createOrderParams);

        String lastorderIdxQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastorderIdxQuery, int.class);
    }
}
