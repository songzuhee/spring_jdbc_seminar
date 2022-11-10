package com.umc.src.user;

import com.umc.src.user.Model.GetUserRes;
import com.umc.config.BaseException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.umc.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class UserProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;

    // 유저 조회
    public GetUserRes getUser(int userIdx) throws BaseException {
        try {
            GetUserRes getUserRes = userDao.selectUser(userIdx);
            return getUserRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }

    // 이메일 중복 검사
    public int checkEmail(String email) throws BaseException{
        try{
            return userDao.checkEmail(email);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }

    }
}
