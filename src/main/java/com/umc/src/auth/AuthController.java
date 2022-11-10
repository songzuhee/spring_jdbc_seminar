package com.umc.src.auth;

import com.umc.src.auth.Model.*;

import com.umc.config.BaseException;
import com.umc.config.BaseResponse;
import com.umc.src.auth.Model.PostLoginReq;
import com.umc.src.auth.Model.PostLoginRes;
import com.umc.src.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



import static com.umc.config.BaseResponseStatus.POST_USERS_EMPTY_ID;
import static com.umc.config.BaseResponseStatus.POST_USERS_EMPTY_PASSWORD;

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
    로그인 API
    [POST] /users/login
     */
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
