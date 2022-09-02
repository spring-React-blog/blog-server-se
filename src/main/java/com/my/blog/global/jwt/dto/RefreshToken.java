package com.my.blog.global.jwt.dto;

import lombok.Getter;

@Getter
public class RefreshToken {

    private String token;
    public RefreshToken(String token){
        this.token = token;
    }
}
