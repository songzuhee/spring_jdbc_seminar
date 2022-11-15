package com.umc.src.auth;

import com.umc.src.auth.Model.*;

import com.umc.config.BaseException;
import com.umc.config.BaseResponse;
import com.umc.src.auth.Model.PostLoginReq;
import com.umc.src.auth.Model.PostLoginRes;
import com.umc.src.utils.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



import static com.umc.config.BaseResponseStatus.POST_USERS_EMPTY_ID;
import static com.umc.config.BaseResponseStatus.POST_USERS_EMPTY_PASSWORD;

@Api
@RestController
@RequestMapping("/auth")
public class AuthController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());


    private final AuthProvider authProvider;

    private final AuthService authService;

    private final JwtService jwtService;


    public AuthController(AuthProvider authProvider, AuthService authService, JwtService jwtService){
        this.authProvider = authProvider;
        this.authService = authService;
        this.jwtService = jwtService;
    }

    /*
     * 로그인 API
     * [POST] /auth/login
     */
    @ApiOperation(value = "로그인 API", notes ="DB 데이터와 일치한다면 JWT 발급하며 로그인 성공")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청 성공"),
            @ApiResponse(code = 4000, message = "서버 에러"),
            @ApiResponse(code = 5001, message = "아이디를 입력하세요."),
            @ApiResponse(code = 5002, message = "비밀번호를 입력하세요. "),
            @ApiResponse(code = 2006, message = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(code = 3014, message = "없는 아이디거나 비밀번호가 틀렸습니다. "),
            @ApiResponse(code = 4011, message = "비밀번호 복호화에 실패하였습니다. ")
    })
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq) {
        try {
            if(postLoginReq.getId() == null)
            {
                return new BaseResponse<>(POST_USERS_EMPTY_ID);
            }
            if(postLoginReq.getPassword() == null)
            {
                return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);

            }

            PostLoginRes postLoginRes = authProvider.logIn(postLoginReq);

            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
