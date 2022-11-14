package com.umc.src.store;

import com.umc.config.BaseException;
import com.umc.config.BaseResponse;
import com.umc.src.store.Model.GetRankingListRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private StoreService storeService;
    private StoreProvider storeProvider;

    @Autowired
    public StoreController(StoreProvider storeProvider, StoreService storeService) {
        this.storeProvider = storeProvider;
        this.storeService = storeService;
    }

    /*
    맛집 랭링 리스트 조회
     */
    @ResponseBody
    @GetMapping("/ranking")
    public BaseResponse<List<GetRankingListRes>> getRankingList() {
        try {
            List<GetRankingListRes> getRankingListRes = storeProvider.RankingList();
            return new BaseResponse<>(getRankingListRes);
        } catch(BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
