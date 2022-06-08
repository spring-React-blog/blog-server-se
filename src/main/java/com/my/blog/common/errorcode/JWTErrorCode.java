package com.my.blog.common.errorcode;

public enum JWTErrorCode implements ErrorCode {

    EXPIRED_TOKEN("400", "M001", "비밀번호가 없습니다."),
    INVALID_TOKEN("400", "M002", "없는 회원 입니다."),
    NON_LOGIN("400", "M002", "없는 회원 입니다."),
    ACCESS_DENIED("400", "M003", "없는 회원 입니다.");

    private final String status;
    private final String code;
    private final String message;

    JWTErrorCode(String status, String code, String message) {
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
