package com.umc.src.Point;

import com.umc.config.BaseException;
import com.umc.config.BaseResponse;
import com.umc.src.Point.Model.GetPointRes;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequiredArgsConstructor
@RequestMapping("/points")
public class PointController {
    private PointService pointService;
    private PointProvider pointProvider;

    @Autowired
    public PointController(PointService pointService, PointProvider pointProvider) {
        this.pointService = pointService;
        this.pointProvider = pointProvider;
    }

    /*
     * 해당 유저 포인트 조회
     * [GET] /points/:userIdx
     */
    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<List<GetPointRes>> getUserPoint(@PathVariable("userIdx")int userIdx) {
        try {
            List<GetPointRes> getPointRes = pointProvider.getUserPointList(userIdx);
            return new BaseResponse<>(getPointRes);
        } catch(BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
