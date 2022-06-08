package com.my.blog.common.errorcode;

public enum MemberErrorCode implements ErrorCode {

    PASSWORD_NULL_ERROR("400", "M001", "비밀번호가 없습니다."),
    USER_NOT_FOUND("400", "M002", "없는 회원 입니다."),
    NON_LOGIN("400", "M002", "없는 회원 입니다.");

    private final String status;
    private final String code;
    private final String message;

    MemberErrorCode(String status, String code, String message) {
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
