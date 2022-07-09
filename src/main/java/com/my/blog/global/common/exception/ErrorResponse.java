package com.my.blog.global.common.exception;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private String message;
    public ErrorResponse(final String message){
        this.message = message;
    }


}
