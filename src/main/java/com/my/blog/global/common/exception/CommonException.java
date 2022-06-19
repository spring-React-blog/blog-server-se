package com.my.blog.global.common.exception;

import com.my.blog.global.common.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public class CommonException extends RuntimeException{
    private ErrorCode errorCode;

    public CommonException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
