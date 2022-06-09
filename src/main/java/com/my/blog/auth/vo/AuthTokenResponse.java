package com.my.blog.auth.vo;

import com.my.blog.jwt.dto.AccessToken;
import com.my.blog.jwt.dto.RefreshToken;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class AuthTokenResponse {

    private AccessToken accessToken;
    private RefreshToken refreshToken;

}
