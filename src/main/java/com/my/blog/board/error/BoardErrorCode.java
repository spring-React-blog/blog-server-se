package com.my.blog.board.error;

import com.my.blog.global.common.errorcode.ErrorCode;
import org.springframework.http.HttpStatus;

public enum BoardErrorCode implements ErrorCode {

    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND,  "해당 게시판을 찾을 수 없습니다."),
    NOT_AUTHORIZED(HttpStatus.BAD_REQUEST, "해당 회원의 게시판이 아닙니다");

    private final HttpStatus status;
    private final String message;

    BoardErrorCode(HttpStatus status, String message) {
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
