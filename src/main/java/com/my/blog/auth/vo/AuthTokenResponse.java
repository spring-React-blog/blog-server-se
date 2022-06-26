package com.my.blog.auth.vo;

import com.my.blog.global.jwt.dto.AccessToken;
import com.my.blog.global.jwt.dto.RefreshToken;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class AuthTokenResponse {

    private AccessToken accessToken;

}
