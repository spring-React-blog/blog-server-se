package com.my.blog.common.errorcode;

public enum BoardErrorCode implements ErrorCode {

    BOARD_NOT_FOUND("400", "B001", "해당 게시판을 찾을 수 없습니다."),
    NOT_AUTHORIZED("400", "B002", "해당 회원의 게시판이 아닙니다");

    private final String status;
    private final String code;
    private final String message;

    BoardErrorCode(String status, String code, String message) {
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
    public String getStatus() {
        return this.status;
    }
}
