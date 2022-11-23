package com.umc.src.zzim;

import com.umc.config.BaseException;
import com.umc.config.BaseResponse;
import com.umc.src.utils.JwtService;
import com.umc.src.zzim.Model.GetZzimRes;
import com.umc.src.zzim.Model.PostZzimRes;
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
@RequestMapping("/zzims")
public class ZzimController {

    private ZzimProvider zzimProvider;
    private ZzimService zzimService;
    private JwtService jwtService;

    @Autowired
    public ZzimController(ZzimProvider zzimProvider, ZzimService zzimService, JwtService jwtService) {
        this.zzimProvider = zzimProvider;
        this.zzimService = zzimService;
        this.jwtService = jwtService;
    }

    /*
     * 찜 생성 API
     * [POST] /zzims/:userIdx/:storeIdx
     */
    @ApiOperation(value = "찜 생성 API", notes = "PathVariable로 받은 userIdx가 선택 storeIdx의 " +
            "해당하는 가게를 찜 ")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청 성공"),
            @ApiResponse(code = 4000, message = "서버 에러")
    })
    @ResponseBody
    @PostMapping("/{userIdx}/{storeIdx}")
    public BaseResponse<PostZzimRes> createZzim(@PathVariable("userIdx")int userIdx,
           @PathVariable("storeIdx")int storeIdx) {
        try {
            PostZzimRes postZzimRes = zzimService.createZzim(userIdx, storeIdx);
            return new BaseResponse<>(postZzimRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /*
     * 찜 리스트 조회
     * [GET] /zzims/:userIdx
     */
    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<List<GetZzimRes>> getZzimList(@PathVariable("userIdx")int userIdx) {
        try {
            List<GetZzimRes> getZzimRes = zzimProvider.UserZzimList(userIdx);
            return new BaseResponse<>(getZzimRes);
        } catch(BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /*
     * 찜 취소 API
     * [PATCH] /zzims/:userIdx
     */
    @ResponseBody
    @PatchMapping("/inactive/{userIdx}/{storeIdx}")
    public BaseResponse<String> inactiveZzim(@PathVariable("userIdx")int userIdx, @PathVariable("storeIdx")int storeIdx) {
        try {

            zzimService.inactiveZzim(userIdx, storeIdx);

            String result = "찜 취소되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
