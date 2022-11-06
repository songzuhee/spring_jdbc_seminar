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

    POST_EMPTY_CONTENT(false, 2000, "내용을 입력해주세요."),
    /**
     * 4000 : Database, Server 오류
     */

    // Common - 0xx
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    // Post
    CREATE_FAIL_POST(false, 4002, "게시글 생성을 실패하였습니다."),
    DELETE_FAIL_POST(false, 4003, "게시글 삭제를 실패하였습니다."),
    UPDATE_FAIL_POST(false, 4004, "게시글 수정을 실패하였습니다. ");
    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}


