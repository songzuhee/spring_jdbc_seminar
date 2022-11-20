package com.umc.src.store;

import com.umc.config.BaseException;
import com.umc.config.BaseResponseStatus;
import com.umc.src.store.Model.GetDetailRes;
import com.umc.src.store.Model.GetRankingListRes;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final StoreDao storeDao;

    // 가게 상세 조회
    public GetDetailRes retrieveStore(int storeIdx) throws BaseException {
        try {
            GetDetailRes getDetailRes = storeDao.selectStore(storeIdx);
            return getDetailRes;
        }   catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    // 맛집 랭킹 리스트 조회
    public List<GetRankingListRes> RankingList() throws BaseException {
        try {
            List<GetRankingListRes> getRankingListRes = storeDao.selectRanking();
            return getRankingListRes;
        } catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
