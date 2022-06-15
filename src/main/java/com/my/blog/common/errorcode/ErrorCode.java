package com.my.blog.common.errorcode;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    public String getCode();
    public String getMessage();
    public HttpStatus getStatus();
}

