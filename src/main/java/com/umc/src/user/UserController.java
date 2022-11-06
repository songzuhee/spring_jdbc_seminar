package com.umc.src.user;

import com.umc.src.user.Model.GetUserRes;
import com.umc.config.BaseException;
import com.umc.config.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private UserProvider userProvider;

    @Autowired
    public UserController(UserProvider userProvider) {
        this.userProvider = userProvider;
    }

    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<GetUserRes> getUser(@PathVariable("userIdx")int userIdx) {
        try {

            GetUserRes getUserRes = userProvider.getUser(userIdx);
            return new BaseResponse<>(getUserRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
