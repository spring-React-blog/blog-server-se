package com.my.blog.auth.controller;

import com.my.blog.auth.service.AuthService;
import com.my.blog.auth.vo.AuthTokenResponse;
import com.my.blog.auth.vo.LoginRequest;
import com.my.blog.common.response.ResponseEnvelope;
import com.my.blog.common.response.ResponseHeader;
import com.my.blog.jwt.dto.AccessToken;
import com.my.blog.jwt.dto.TokenDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/join")
    public ResponseEnvelope<AuthTokenResponse> join(@RequestBody LoginRequest request){
        TokenDTO token = authService.login(request.getEmail(), request.getPassword());

        AuthTokenResponse response = AuthTokenResponse.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();

        return new ResponseEnvelope<>(ResponseHeader.ok(),response);
    }

    @PostMapping("/auth/login")
    public ResponseEnvelope<AuthTokenResponse> login(@RequestBody LoginRequest request){
        TokenDTO token = authService.login(request.getEmail(), request.getPassword());

        AuthTokenResponse response = AuthTokenResponse.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();

        return new ResponseEnvelope<>(ResponseHeader.ok(),response);
    }

    @PostMapping("/auth/reissue")
    public ResponseEnvelope<AuthTokenResponse> reissue(@RequestBody String refreshToken){

        AccessToken token = authService.reissue(refreshToken);
        AuthTokenResponse response = AuthTokenResponse.builder().accessToken(token).build();
        return new ResponseEnvelope<>(ResponseHeader.ok(),response);

    }
}
