package com.umc.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /**
     * 2000 : Request 오류
     */

    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    POST_EMPTY_CONTENT(false, 2000, "내용을 입력해주세요."),
    DUPLICATED_EMAIL(false, 2004, "중복된 이메일입니다."),

    FALSE_PWD(false, 2005, "비밀번호가 일치하지 않습니다."),
    INACTIVE_ACCOUNT(false, 2006, "권한이 없는 유저의 접근입니다. "),


    /**
     * 4000 : Database, Server 오류
     */

    // Common - 0xx
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    // Post
    CREATE_FAIL_POST(false, 4002, "게시글 생성을 실패하였습니다."),
    DELETE_FAIL_POST(false, 4003, "게시글 삭제를 실패하였습니다."),
    UPDATE_FAIL_POST(false, 4004, "게시글 수정을 실패하였습니다. "),
    MODIFY_FAIL_USERNAME(false, 4005, "프로필 수정을 실패하였습니다."),

    // USER
    DELETE_FAIL_USER(false, 4006, "회원 삭제를 실패하였습니다. "),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),
    USERS_EMPTY_USER_ID(false, 3015, "없는 계정입니다."),

    // Zzim
    DELETE_FAIL_ZZIM(false, 3016, "찜 취소를 실패하였습니다. "),
    // 5000
    POST_USERS_EMPTY_EMAIL(false, 5000, "이메일을 입력하세요. "),
    POST_USERS_EMPTY_ID(false, 5001, "아이디를 입력하세요. "),
    POST_USERS_EMPTY_PASSWORD(false, 5002, "비밀번호를 입력하세요. "),
    POST_USERS_EMPTY_NICKNAME(false, 5003, "닉네임을 입력하세요. "),
    POST_USERS_EMPTY_PHONE(false, 5004, "전화번호를 입력하세요. "),
    POST_USERS_INVALID_EMAIL(false, 5005, "이메일형식을 확인해주세요."),
    PASSWORD_EXCRYPTION_ERROR(false, 5006, "비밀번호 암호화에 실패했습니다."),
    ORDER_EMPTY_METHOD(false, 5007, "결제 방식을 선택해주세요."),
    ORDER_EMPTY_PRICE(false, 5008,"금액을 입력하세요."),
    REVIEW_EMPTY_RATING(false, 5009, "별점을 입력하세요."),
    REVIEW_EMPTY_CONTENT(false, 5010, "리뷰 내용을 입력하세요. "),
    POST_USER_INVALID_PASSWORD(false, 5011, "영문, 특수문자, 숫자 포함 8자 이상으로 설정해주세요."),
    POST_USER_INVALID_PASSWORD2(false, 5012, "반복된 문자는 안됩니다."),

    EMPTY_IMGURL(false, 5013, "이미지를 첨부해주세요."),
    // 6000
    PASSWORD_DECRYPTION_ERROR(false, 4011, "비밀번호 복호화에 실패하였습니다.");
    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}

