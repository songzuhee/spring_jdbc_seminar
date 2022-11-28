package com.umc.src.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.umc.src.s3.S3Service;
import com.umc.src.user.Model.*;
import com.umc.config.BaseException;
import com.umc.config.BaseResponse;
import com.umc.src.utils.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

import static com.umc.config.BaseResponseStatus.*;
import static com.umc.src.utils.ValidationRegex.*;

@Api
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private UserProvider userProvider;
    private UserService userService;
    private JwtService jwtService;
    private S3Service s3Service;

    private JavaMailSender javaMailSender;
    SendToMeDto sendToMeDto = new SendToMeDto();

    //@Value("${spring.mail.username}")
    //private String from;
    @Autowired
    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService, JavaMailSender javaMailSende, S3Service s3Service) {
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
        this.javaMailSender = javaMailSender;
        this.s3Service = s3Service;

    }


    /*
     * 전체 유저 조회 API
     * [GET] /users/
     */
    @ApiOperation(value = "전체 유저 조회 API", notes ="DB에 저장된 모든 유저 조회")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청 성공"),
            @ApiResponse(code = 4000, message = "서버 에러")
    })
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
     * 유저 조회 API
     * [GET] /users/:userIdx
     */
    @ApiOperation(value = "유저 조회 API", notes ="PathVariable로 userIdx 받아와서 유저 상세조회")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청 성공"),
            @ApiResponse(code = 4000, message = "서버 에러")
    })
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
     * 프로필 수정 API
     * [PATCH] /users/:userIdx
     */
    @ApiOperation(value = "프로필 수정 API", notes ="PathVariable로 받아온 userIdx 의 프로필 수정")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청 성공"),
            @ApiResponse(code = 4000, message = "서버 에러"),
            @ApiResponse(code = 2002, message = "유효하지 않는 JWT입니다. "),
            @ApiResponse(code = 5000, message = "이메일을 입력하세요. "),
            @ApiResponse(code = 5002, message = "비밀번호를 입력하세요. "),
            @ApiResponse(code = 5003, message = "닉네임을 입력하세요. "),
            @ApiResponse(code = 5004, message = "전화번호를 입력하세요. "),
            @ApiResponse(code = 3015, message = "없는 계정입니다. "),
            @ApiResponse(code = 5006, message = "비밀번호 암호화에 실패했습니다. "),
            @ApiResponse(code = 4005, message = "프로필 수정을 실패하였습니다. ")
    })
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
     * 회원가입 API
     * [POST] /users/sign-in
     */
    @ApiOperation(value = "회원가입 API", notes ="email, password 입력받아 회원가입, 비밀번호는 암호화되어 DB에 저장")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청 성공"),
            @ApiResponse(code = 4000, message = "서버 에러"),
            @ApiResponse(code = 5000, message = "이메일을 입력하세요. "),
            @ApiResponse(code = 5001, message = "아이디를 입력하세요."),
            @ApiResponse(code = 5002, message = "비밀번호를 입력하세요. "),
            @ApiResponse(code = 5003, message = "닉네임을 입력하세요. "),
            @ApiResponse(code = 5004, message = "전화번호를 입력하세요. "),
            @ApiResponse(code = 2004, message = "중복된 이메일입니다."),
            @ApiResponse(code = 2005, message = "비밀번호가 일치하지 않습니다."),
            @ApiResponse(code = 5011, message = "영문, 특수문자, 숫자 포함 8자 이상으로 비밀번호를 설정해주세요.")
    })
    @ResponseBody
    @PostMapping(value = "/sign-in", consumes = {"multipart/form-data"})
    public BaseResponse<PostJoinRes> createUser(@RequestParam("jsonList")String jsonList,
                                                @RequestPart(value = "images", required = false) List<MultipartFile> MultipartFiles) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        PostJoinReq postJoinReq = objectMapper.readValue(jsonList, new TypeReference<>() {
        });
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
            if (MultipartFiles == null) {
                return new BaseResponse<>(EMPTY_IMGURL);
            }

            // 이메일 정규 표현
            if (!isRegexEmail(postJoinReq.getEmail())) {
                return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
            }
            if (!isRegexPassword1(postJoinReq.getPassword())) {
                return new BaseResponse<>(POST_USER_INVALID_PASSWORD);
            }


            PostJoinRes postJoinRes = userService.createUser(postJoinReq, MultipartFiles);
            return new BaseResponse<>(postJoinRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /*
     * 유저 삭제 API
     * [PATCH] /users/inactive/:userIdx
     */
    @ApiOperation(value = "유저 삭제 API", notes ="PathVariable로 받아온 userIdx의 status -> 'INACTIVE'로 변경")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청 성공"),
            @ApiResponse(code = 4000, message = "서버 에러"),
            @ApiResponse(code = 2002, message = "유효하지 않는 JWT입니다. "),
            @ApiResponse(code = 4006, message = "회원 삭제를 실패하였습니다.")
    })
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

    @Transactional
    @PostMapping("/mail")
    public BaseResponse<String> mailPassword(@RequestParam("email")String email) {
        MailDto dto = userService.createMailAndChangePasword(email);
        userService.mailSend(dto);

        String result = "메일 전송 되었습니다. ";
        return new BaseResponse<>(result);
    }
}
