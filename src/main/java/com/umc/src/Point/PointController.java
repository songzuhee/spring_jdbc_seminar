package com.umc.src.Point;

import com.umc.config.BaseException;
import com.umc.config.BaseResponse;
import com.umc.src.Point.Model.GetPointRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequiredArgsConstructor
@RequestMapping("/points")
public class PointController {
    private PointProvider pointProvider;

    @Autowired
    public PointController(PointProvider pointProvider) {
        this.pointProvider = pointProvider;
    }

    /*
     * 해당 유저 포인트 조회
     * [GET] /points/:userIdx
     */
    @ApiOperation(value = "포인트 조회 API", notes = "{userIdx}에 일치하는 유저의 포인트 조회")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청 성공"),
            @ApiResponse(code = 4000, message = "서버 에러"),
    })
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
