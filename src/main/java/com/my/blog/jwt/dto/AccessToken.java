package com.my.blog.jwt.dto;

import lombok.Getter;

@Getter
public class AccessToken {
    private String token;
    public AccessToken(String token){
        this.token = token;
    }
}
