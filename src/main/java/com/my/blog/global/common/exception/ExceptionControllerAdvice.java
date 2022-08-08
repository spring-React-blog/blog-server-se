package com.my.blog.global.common.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.my.blog.global.common.errorcode.ErrorCode;
import com.my.blog.global.jwt.error.JWTErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e){
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = e.getFieldErrors();

        StringBuffer sb = new StringBuffer();
        for(FieldError fieldError : fieldErrors){
            String field = fieldError.getField();
            String code = fieldError.getCode();
            String defaultMessage = fieldError.getDefaultMessage();
            log.info("bind error field = {}, {}, {}" , field ,code, defaultMessage );
            if (sb.length()>0) sb.append(", ");
            sb.append(field + " : " + defaultMessage);

        }

        return new ResponseEntity<>(new ErrorResponse(sb.toString()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFormatException.class)
    protected ResponseEntity<ErrorResponse> handleExpiredJwtException(InvalidFormatException e){
        log.info("InvalidFormatException >" ,e);
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()),HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ExpiredJwtException.class)
    protected ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException e){
        log.info("ExpiredJwtException >" ,e);
        return new ResponseEntity<>(new ErrorResponse(JWTErrorCode.EXPIRED_TOKEN.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CommonException.class)
    protected ResponseEntity<ErrorResponse> handleCommonException(CommonException e){
        ErrorCode errorCode = e.getErrorCode();
        log.info("CommonException >" ,errorCode);

        return new ResponseEntity<>(new ErrorResponse(e.getMessage()),HttpStatus.BAD_REQUEST);
    }

   @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ErrorResponse> runtimeException(RuntimeException e){
       log.info("RuntimeException = {}" , e);
       String message = "";
       if(e.getMessage()==null){
           message = e.getClass().toString();
       }
        return new ResponseEntity<>(new ErrorResponse(message),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> exception(Exception e){
        log.info("Exception = {}" , e);
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
