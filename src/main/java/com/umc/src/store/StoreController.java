package com.umc.src.store;

import com.umc.config.BaseException;
import com.umc.config.BaseResponse;
import com.umc.src.store.Model.GetRankingListRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
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
     * 맛집 랭링 리스트 조회 API
     * [GET] /stores/ranking
     */
    @ApiOperation(value = "맛집 랭킹 리스트 조회 API", notes ="랭킹 기준 -> 리뷰 개수")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청 성공"),
            @ApiResponse(code = 4000, message = "서버 에러")
    })
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
