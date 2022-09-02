package com.my.blog.global.jwt.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public class AccessToken {
    private String token;
    @JsonValue
    public String accessToken(){
        return this.token;
    }
    public AccessToken(String token){
        this.token = token;
    }
}
