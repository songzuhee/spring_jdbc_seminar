package com.umc.src.store;

import com.umc.src.store.Model.GetRankingListRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class StoreDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 맛집 랭킹
    public List<GetRankingListRes> selectRanking() {
        String selectRankingListQuery = "select s.storeIdx, s.food_category, s.name,count(r.rating) as countrating,\n" +
                "       s.min_delivery_price, s.delivery_tip, s.delivery_time, s.store_img from Review as r\n" +
                "    left join Store as s\n" +
                "    on r.storeIdx = s.storeIdx\n" +
                "group by s.storeIdx\n" +
                "order by countrating desc; ";

        return this.jdbcTemplate.query(selectRankingListQuery,
                (rs, rowNum) -> new GetRankingListRes(
                        rs.getInt("storeIdx"),
                        rs.getString("food_category"),
                        rs.getString("name"),
                        rs.getInt("countRating"),
                        rs.getInt("min_delivery_price"),
                        rs.getInt("delivery_tip"),
                        rs.getInt("delivery_time"),
                        rs.getString("store_img")
                ));
    }
}
