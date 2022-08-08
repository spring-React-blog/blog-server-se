package com.my.blog.category.error;

import com.my.blog.global.common.errorcode.ErrorCode;
import org.springframework.http.HttpStatus;

public enum CategoryError implements ErrorCode {

    NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 카테고리입니다.");

    private final HttpStatus status;
    private final String message;

    CategoryError(HttpStatus status, String message) {
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
