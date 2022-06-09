package com.my.blog.jwt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TokenDTO {

    public TokenDTO(AccessToken accessToken,RefreshToken refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    @JsonProperty("access_token")
    private AccessToken accessToken;

    @JsonProperty("refresh_token")
    private RefreshToken refreshToken;

}
