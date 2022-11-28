package com.umc.src.user;

import com.umc.config.BaseException;
import com.umc.src.auth.Model.PostLoginReq;
import com.umc.src.auth.Model.PostLoginRes;
import com.umc.src.s3.S3Service;
import com.umc.src.user.Model.*;
import com.umc.src.utils.AES256;
import com.umc.src.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;
import java.net.Authenticator;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static com.umc.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class UserService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;
    private final JavaMailSender javaMailSender;

    private final S3Service s3Service;
    // 회원가입
    @Transactional(rollbackFor = BaseException.class)
    public PostJoinRes createUser(PostJoinReq postJoinReq, List<MultipartFile> MultipartFiles) throws BaseException {
        // 중복 검사
        if (userProvider.checkEmail(postJoinReq.getEmail()) == 1) {
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
            if(MultipartFiles != null) {
                for (int i = 0; i < MultipartFiles.size(); i++) {

                    //s3 업로드
                    String s3path = "userPicture/userIdx: " + Integer.toString(userIdx);
                    String imgPath = s3Service.uploadFile(MultipartFiles.get(i), s3path);

                    // db업로드
                    s3Service.uploadUserPicture(imgPath, userIdx);
                }
            }
            return new PostJoinRes(userIdx);
        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 프로필 수정
    public void modifyProfile(int userIdx, PostUpdateReq postUpdateReq, int userIdxByJwt) throws BaseException {
        if (userProvider.checkUserExist(userIdx) == 0) {
            throw new BaseException(USERS_EMPTY_USER_ID);

        }

        // 비밀번호 암호화
        String password;
        try {
            password = new AES256().encrypt(postUpdateReq.getPassword());
            postUpdateReq.setPassword(password);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_EXCRYPTION_ERROR);
        }
        try {
            int result = userDao.updateProfile(userIdx, postUpdateReq, userIdxByJwt);
            if (result == 0) {
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 유저 삭제
    public void inactiveUser(int userIdx) throws BaseException {
        try {
            int result = userDao.updateUserStatus(userIdx);
            if (result == 0) {
                throw new BaseException(DELETE_FAIL_USER);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }

    // 메일 내용을 생성하고 임시 비밀번호로 회원 비밀번호를 변경
    public MailDto createMailAndChangePasword(String email) {
        String str = getTempPassword();
        MailDto dto = new MailDto();
        dto.setAddress(email);
        dto.setTitle("주희 사이트 임시비밀번호 안내 이메일 입니다. ");
        dto.setMessage("안녕하세요 주희 사이트 임시비밀번호 안내 관련 이메일입니다. " + "회원님의 임시 비밀번호는"
                + str + "입니다. " + "로그인 후에 비밀번호를 변경 해주세요. ");
        updatePassword(str, email);
        return dto;
    }

    //임시 비밀번호로 업데이트
    public void updatePassword(String str, String email) {
        String password = str;
        int userIdx = userDao.findByMemberEmail(email);
        System.out.println(userIdx);
        // 여기서 해당 userIdx 넘기기
        userDao.updatePassword(userIdx, password);
        System.out.println("password" + password);
    }

    // 랜덤함수로 임시비밀번호 구문 만들기
    public String getTempPassword() {
        char[] charSet = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String str = "";

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
        int idx = 0;
        for(int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
            System.out.println("str: "+ str);
        }
        return str;
    }

    // 메일 보내기
    public void mailSend(MailDto mailDto) {
        System.out.println("전송 완료");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        message.setFrom("wngml203@naver.com");
        message.setReplyTo("wngml203@naver.com");
        System.out.println("message" + message);
        javaMailSender.send(message);
    }

    //비밀번호 변경
    public void updatePassWord(int userIdx, String password) {
        userDao.updatePassword(userIdx,password);
        System.out.println(password);
    }
}

