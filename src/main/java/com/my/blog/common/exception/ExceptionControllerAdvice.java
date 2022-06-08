package com.my.blog.common.exception;

import com.my.blog.common.errorcode.ErrorCode;
import com.my.blog.common.response.ResponseEnvelope;
import com.my.blog.common.response.ResponseHeader;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = CommonException.class)
    protected ResponseEnvelope<ErrorResponse> handleCommonException(CommonException e){
        ErrorCode errorCode = e.getErrorCode();
        ResponseHeader header = ResponseHeader.error(errorCode);
        return new ResponseEnvelope<>(header,new ErrorResponse());
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEnvelope<ErrorResponse> handleException(Exception e){

        ResponseHeader header = ResponseHeader.builder()
                .code("500")
                .status(e.getCause().toString())
                .message(e.getMessage())
                .build();
        return new ResponseEnvelope<>(header,new ErrorResponse());
    }
}
