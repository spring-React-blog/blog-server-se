package com.my.blog.global.common.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.my.blog.global.common.errorcode.ErrorCode;
import com.my.blog.global.jwt.error.JWTErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(InvalidFormatException.class)
    protected ResponseEntity<ErrorResponse> handleExpiredJwtException(InvalidFormatException e){
        log.info("e >" ,e);
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()),HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ExpiredJwtException.class)
    protected ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException e){
        log.info("e >" ,e);
        return new ResponseEntity<>(new ErrorResponse(JWTErrorCode.EXPIRED_TOKEN.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CommonException.class)
    protected ResponseEntity<ErrorResponse> handleCommonException(CommonException e){
        ErrorCode errorCode = e.getErrorCode();
        log.info("errorCode >" ,errorCode);

        return new ResponseEntity<>(new ErrorResponse(e.getMessage()),HttpStatus.BAD_REQUEST);
    }

   @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ErrorResponse> runtimeException(RuntimeException e){
       log.info("e = {}" , e);
       String message = "";
       if(e.getMessage()==null){
           message = e.getClass().toString();
       }
        return new ResponseEntity<>(new ErrorResponse(message),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> exception(Exception e){
        log.info("e = {}" , e);
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
