package com.my.blog.count.error;

import com.my.blog.global.common.errorcode.ErrorCode;
import org.springframework.http.HttpStatus;

public enum BoardCountErrorCode implements ErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "해당 정보를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;

    BoardCountErrorCode(HttpStatus status, String message) {
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
