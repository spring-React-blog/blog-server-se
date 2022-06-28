package com.my.blog.global.jwt.error;

import com.my.blog.global.common.errorcode.ErrorCode;
import org.springframework.http.HttpStatus;

public enum JWTErrorCode implements ErrorCode {

    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "인증 토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED,  "유효하지 않은 인증 토큰입니다. "),
    NON_LOGIN(HttpStatus.NOT_ACCEPTABLE, "로그인이 필요한 메뉴입니다."),
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "접근 불가능한 회원입니다.");

    private final HttpStatus status;
    private final String message;

    JWTErrorCode(HttpStatus status, String message) {
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
