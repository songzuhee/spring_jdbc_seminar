package com.umc.src.store;

import com.umc.src.store.Model.GetDetailRes;
import com.umc.src.store.Model.GetRankingListRes;
import com.umc.src.store.Model.GetStoreMenuRes;
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

    List<GetStoreMenuRes> getStoreMenuRes;
    // 가게 상세 조회
    public GetDetailRes selectStore(int storeIdx) {
        String selectStoreQuery = "select s.storeIdx, s.name as storeName, s.min_delivery_price, s.delivery_tip,\n" +
                "       s.operatingTime, s.delivery_time, s.phoneNumber, s.owner, s.delivery_address, s.address,\n" +
                "       count(z.zzimIdx) as ZzimCount, s.owner_comment_count as ownerComment,\n" +
                "       avg(r.rating) as Avgrating from Store as s\n" +
                "join Review as  r on s.storeIdx = r.storeIdx\n" +
                "join Zzim as z on z.storeIdx = s.storeIdx\n" +
                "where s.storeIdx =?;";
        int selectStoreParam = storeIdx;

        return this.jdbcTemplate.queryForObject(selectStoreQuery,
                (rs, rowNum) -> new GetDetailRes(
                        rs.getInt("storeIdx"),
                        rs.getString("storeName"),
                        rs.getInt("Avgrating"),
                        rs.getInt("ownerComment"),
                        rs.getInt("ZzimCount"),
                        rs.getInt("min_delivery_price"),
                        rs.getInt("delivery_time"),
                        rs.getInt("delivery_tip"),
                        rs.getString("operatingTime"),
                        rs.getString("phoneNumber"),
                        rs.getString("owner"),
                        rs.getString("delivery_address"),
                        rs.getString("address"),

                        getStoreMenuRes = this.jdbcTemplate.query(
                                        "select m.name, m.price, m.menu_img from Menu as m\n" +
                                        "join Store as s\n" +
                                        "on s.storeIdx = m.storeIdx\n" +
                                        "where s.storeIdx = ?;",
                                (rk, rownum) -> new GetStoreMenuRes(
                                        rk.getString("name"),
                                        rk.getString("price"),
                                        rk.getString("menu_img")
                                ), selectStoreParam)
                ), selectStoreParam);
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
