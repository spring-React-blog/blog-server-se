package com.my.blog.auth.controller;

import com.my.blog.auth.service.AuthService;
import com.my.blog.auth.vo.AuthTokenResponse;
import com.my.blog.auth.vo.LoginRequest;
import com.my.blog.global.jwt.dto.AccessToken;
import com.my.blog.global.jwt.dto.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AuthController {
    private final AuthService authService;

    /**
     * 로그인 요청
     * @input  LoginRequest
     * @output AuthTokenResponse(AccessToken, RefreshToken)
     * */
    @PostMapping("/public/auth/login")
    public ResponseEntity<AuthTokenResponse> login(@RequestBody LoginRequest request, HttpServletResponse response){
        TokenDTO token = authService.login(request.getEmail(), request.getPassword());

        AuthTokenResponse tokenResponse = AuthTokenResponse.builder()
                .accessToken(token.getAccessToken())
                .build();

        Cookie cookie = getRefreshTokenCookie( token.getRefreshToken().getToken());
        response.addCookie(cookie);
        return new ResponseEntity<>(tokenResponse,HttpStatus.OK);
    }

    /**
     * AccessToken 재발행 요청
     * @input  RefreshToken
     * @output AccessToken
     * */
    @PostMapping("/public/auth/refresh")
    public ResponseEntity<AuthTokenResponse> issueAccessToken(@CookieValue(name = "refreshToken")  String refreshToken
            , HttpServletResponse response){
        //refresh token 도 만료되었을 경우 로그아웃
        AccessToken token = authService.issueAccessToken(refreshToken);
        AuthTokenResponse tokenResponse = AuthTokenResponse.builder().accessToken(token).build();

        Cookie cookie = getRefreshTokenCookie( refreshToken);
        response.addCookie(cookie);

        return new ResponseEntity<>(tokenResponse,HttpStatus.OK);

    }

    private Cookie getRefreshTokenCookie(String refreshToken){
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        return cookie;
    }
    /**
     * RefreshToken 삭제
     * @input  RefreshToken
     * @output null
     * */
    @PostMapping("/auth/logout")
    public ResponseEntity<AuthTokenResponse> delete(@RequestBody String refreshToken, HttpServletResponse response){
        Cookie cookie = new Cookie("refreshToken", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);  
        return new ResponseEntity<>(null,HttpStatus.OK);

    }
}
