package com.umc.src.user;

import com.umc.src.auth.Model.PostLoginReq;
import com.umc.src.auth.Model.PostLoginRes;
import com.umc.src.user.Model.*;
import com.umc.config.BaseException;
import com.umc.config.BaseResponse;
import com.umc.src.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.umc.config.BaseResponseStatus.*;
import static com.umc.src.utils.ValidationRegex.isRegexEmail;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private UserProvider userProvider;
    private UserService userService;
    private JwtService jwtService;

    @Autowired
    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService) {
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /*
    전체 유저 조회
     */
    @ResponseBody
    @GetMapping("/")
    public BaseResponse<List<GetUserListRes>> getUserList() {
        try {
            List<GetUserListRes> getUserListRes = userProvider.UserList();
            return new BaseResponse<>(getUserListRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /*
    유저 조회
     */
    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<GetUserRes> getUser(@PathVariable("userIdx") int userIdx) {
        try {

            GetUserRes getUserRes = userProvider.getUser(userIdx);
            return new BaseResponse<>(getUserRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /*
    프로필 수정
     */
    @ResponseBody
    @PatchMapping("/{userIdx}")
    public BaseResponse<String> modifyProfile(@PathVariable("userIdx")int userIdx, @RequestBody PostUpdateReq postUpdateReq) {

        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_JWT);
            }

            if (postUpdateReq.getEmail() == null) {
                return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
            }
            if (postUpdateReq.getPassword() == null) {
                return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
            }
            if (postUpdateReq.getNickName() == null) {
                return new BaseResponse<>(POST_USERS_EMPTY_NICKNAME);
            }
            if (postUpdateReq.getPhoneNumber() == null) {
                return new BaseResponse<>(POST_USERS_EMPTY_PHONE);
            }

            userService.modifyProfile(userIdx, postUpdateReq, userIdxByJwt);
            String result = "회원정보 수정을 완료하였습니다. ";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /*
    회원가입 API
    [POST] /users/sign-in
     */
    @ResponseBody
    @PostMapping("/sign-in")
    public BaseResponse<PostJoinRes> createUser(@RequestBody PostJoinReq postJoinReq) {
        try {

            if (postJoinReq.getEmail() == null) {
                return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
            }
            if (postJoinReq.getId() == null) {
                return new BaseResponse<>(POST_USERS_EMPTY_ID);
            }
            if (postJoinReq.getPassword() == null) {
                return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
            }
            if (postJoinReq.getNickName() == null) {
                return new BaseResponse<>(POST_USERS_EMPTY_NICKNAME);
            }
            if (postJoinReq.getPhoneNumber() == null) {
                return new BaseResponse<>(POST_USERS_EMPTY_PHONE);
            }

            // 이메일 정규 표현
            if (!isRegexEmail(postJoinReq.getEmail())) {
                return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
            }

            PostJoinRes postJoinRes = userService.createUser(postJoinReq);
            return new BaseResponse<>(postJoinRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /*
    유저 삭제 API
     */
    @ResponseBody
    @PatchMapping("/inactive/{userIdx}")
    public BaseResponse<String> inactiveUser(@PathVariable("userIdx") int userIdx) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_JWT);
            }
            userService.inactiveUser(userIdx);

            String result = "유저 삭제되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
