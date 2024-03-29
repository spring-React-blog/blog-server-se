package com.my.blog.member.error;

import com.my.blog.global.common.errorcode.ErrorCode;
import org.springframework.http.HttpStatus;

public enum MemberErrorCode implements ErrorCode {
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 회원입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "유효하지 않은 비밀번호입니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 회원 입니다."),
    USER_HAS_DELETED(HttpStatus.BAD_REQUEST,"탈퇴한 회원입니다."),
    NON_LOGIN(HttpStatus.UNAUTHORIZED, "로그인이 필요한 메뉴 입니다.");

    private final HttpStatus status;
    private final String message;

    MemberErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public HttpStatus getStatus() {
        return this.status;
    }
}
