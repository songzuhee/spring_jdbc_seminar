package com.umc.src.auth;

import com.umc.config.BaseException;
import com.umc.config.Secret.Secret;
import com.umc.src.auth.Model.PostLoginReq;
import com.umc.src.auth.Model.PostLoginRes;
import com.umc.src.auth.Model.User;
import com.umc.src.utils.AES256;
import com.umc.src.utils.JwtService;
import com.umc.src.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.umc.config.BaseResponseStatus.*;


@Service
public class AuthProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AuthDao authDao;
    private final JwtService jwtService;

    @Autowired
    public AuthProvider(AuthDao authDao, JwtService jwtService) {
        this.authDao = authDao;
        this.jwtService = jwtService;
    }

    // 이메일 확인
    public String checkUserStatus(String id) throws BaseException{
        //   try{
        return authDao.checkUserStatus(id);
        // } catch (Exception exception){
        //   throw new BaseException(DATABASE_ERROR);
        //}
    }

    // 로그인
    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException {
        User user = authDao.getPassword(postLoginReq);
        String password;

        if(checkUserStatus(postLoginReq.getId()).equals("INACTIVE")){
            throw new BaseException(INACTIVE_ACCOUNT);
        }

        try {
            password = new SHA256().encrypt(postLoginReq.getPassword());
            postLoginReq.setPassword(password);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }

        if(postLoginReq.getPassword().equals(password)){
            int userIdx = authDao.getPassword(postLoginReq).getUserIdx();
            System.out.println(userIdx);
            String jwt = jwtService.createJwt(userIdx);
            System.out.println(jwt);
            return new PostLoginRes(userIdx,jwt);
        }
        else{
            throw new BaseException(FAILED_TO_LOGIN);
        }

    }
}
