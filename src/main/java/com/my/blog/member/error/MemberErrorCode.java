package com.my.blog.member.error;

import com.my.blog.common.errorcode.ErrorCode;
import org.springframework.http.HttpStatus;

public enum MemberErrorCode implements ErrorCode {

    PASSWORD_NULL_ERROR(HttpStatus.BAD_REQUEST, "M001", "비밀번호가 없습니다."),
    USER_NOT_FOUND(HttpStatus.NO_CONTENT, "M002", "없는 회원 입니다."),
    NON_LOGIN(HttpStatus.NOT_ACCEPTABLE, "M002", "로그인이 필요한 메뉴 입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    MemberErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
    @Override
    public String getCode() {
        return this.code;
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
