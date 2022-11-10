package com.umc.src.user;

import com.umc.config.BaseException;
import com.umc.config.BaseResponseStatus;
import com.umc.src.user.Model.PostJoinReq;
import com.umc.src.user.Model.PostJoinRes;
import com.umc.src.utils.AES256;
import com.umc.src.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.umc.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class UserService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;

    // 회원가입
    @Transactional
    public PostJoinRes createUser(PostJoinReq postJoinReq) throws BaseException {
        // 중복 검사
        if (userProvider.checkEmail(postJoinReq.getEmail()) == 1){
            throw new BaseException(DUPLICATED_EMAIL);
        }
        // 입력 비밀번호 일치 여부 확인
        if (postJoinReq.getPassword().equals(postJoinReq.getCheckpwd()) == false) {
            throw new BaseException(FALSE_PWD);
        }

        // 암호화
        String password;
        try {
            password = new AES256().encrypt(postJoinReq.getPassword());
            postJoinReq.setPassword(password);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_EXCRYPTION_ERROR);
        }

        try {
            int userIdx = userDao.createUser(postJoinReq);
            return new PostJoinRes(userIdx);
        } catch(Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
