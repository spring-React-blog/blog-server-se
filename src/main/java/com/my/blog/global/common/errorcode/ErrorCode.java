package com.my.blog.global.common.errorcode;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    public String getMessage();
    public HttpStatus getStatus();
}

