package com.my.blog.global.common.exception;

import com.my.blog.global.common.errorcode.ErrorCode;
import com.my.blog.global.common.response.ResponseEnvelope;
import com.my.blog.global.common.response.ResponseHeader;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionControllerAdvice  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CommonException.class)
    protected ResponseEnvelope<ErrorResponse> handleCommonException(CommonException e){
        ErrorCode errorCode = e.getErrorCode();
        ResponseHeader header = ResponseHeader.error(errorCode);
        return new ResponseEnvelope<>(header,new ErrorResponse());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEnvelope<ErrorResponse> handleException(Exception e){

        ResponseHeader header = ResponseHeader.builder()
                .code(e.getCause().toString())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(e.getMessage())
                .build();
        return new ResponseEnvelope<>(header,new ErrorResponse());
    }
//ValidationResult.create(bindException, messageSource, locale);
    @ExceptionHandler(BindException.class)
    public ResponseEnvelope<ErrorResponse>  handleBindException(BindException e) {
        ResponseHeader header = ResponseHeader.builder()
                .code(e.getCause().toString())
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .build();
        return new ResponseEnvelope<>(header,new ErrorResponse());
    }
}
