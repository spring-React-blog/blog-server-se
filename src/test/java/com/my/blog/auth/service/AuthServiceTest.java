package com.my.blog.auth.service;

import com.my.blog.global.jwt.TokenProperties;
import com.my.blog.global.jwt.TokenProvider;
import com.my.blog.global.jwt.dto.AccessToken;
import com.my.blog.global.jwt.dto.TokenDTO;
import com.my.blog.global.security.CustomUserDetails;
import com.my.blog.global.security.dto.LoginAuth;
import com.my.blog.member.entity.vo.Email;
import com.my.blog.member.entity.vo.Password;
import com.my.blog.member.entity.vo.RoleType;
import com.my.blog.member.repository.MemberRepository;
import com.my.blog.support.service.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@UnitTest
class AuthServiceTest {
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    MemberRepository memberRepository;

    private TokenProvider tokenProvider;
    private AuthService authService;
    private TokenProperties tokenProperties;
    private Email email = Email.from("seung90@gmail.com");
    private Password pwd = Password.from("q1w2e3r4!");
    @BeforeEach
    public void init(){
        String secret = "jwtproviderSecretkey-test-authServicetest-jwtproviderSecretkey-test-authServicetest-jwtproviderSecretkey-test-authServicetest";
        this.tokenProperties = new TokenProperties(secret,3000,3000);
        this.tokenProvider = new TokenProvider(tokenProperties);
        this.authService = new AuthService(memberRepository,authenticationManager,tokenProvider);
    }

    @Test
    @DisplayName("로그인")
    public void login(){
        //given

        UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(email.getEmail(), pwd.getPassword());
        Authentication authentication = new UsernamePasswordAuthenticationToken(email.getEmail(), null, CustomUserDetails.getAuthorities(RoleType.USER));

        given(authenticationManager.authenticate(loginToken)).willReturn(authentication);

        //when
        TokenDTO token = authService.login(email, pwd);

        //then
        assertThat(tokenProvider.getIssuer(token.getAccessToken().getToken())).isEqualTo(email.getEmail());
        assertThat(tokenProvider.getIssuer(token.getRefreshToken().getToken())).isEqualTo(email.getEmail());
        assertThat(tokenProvider.getRoles(token.getAccessToken().getToken())).isEqualTo(List.of(RoleType.USER.name()));
        assertThat(tokenProvider.getRoles(token.getRefreshToken().getToken())).isEqualTo(List.of(RoleType.USER.name()));
    }

    @Test
    @DisplayName("액세스 토큰 재발행")
    public void issueAccessToken(){
        //given
        TokenDTO generated = tokenProvider.generate(email.getEmail(), List.of(RoleType.USER.name()));
        String refresh = generated.getRefreshToken().getToken();
        LoginAuth loginAuth = LoginAuth.builder()
                .email(email)
                .password(pwd)
                .roleType(RoleType.USER)
                .build();
        given(memberRepository.findByLoginEmail(email)).willReturn(Optional.of(loginAuth));

        //when
        AccessToken accessToken = authService.issueAccessToken(refresh);

        //then
        assertThat(tokenProvider.getIssuer(accessToken.getToken())).isEqualTo(email.getEmail());
        assertThat(tokenProvider.isTokenExpired(accessToken.getToken())).isFalse();
        assertThat(tokenProvider.validateToken(accessToken.getToken())).isTrue();
    }

}