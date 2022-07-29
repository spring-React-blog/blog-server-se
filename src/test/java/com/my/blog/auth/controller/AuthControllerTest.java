package com.my.blog.auth.controller;

import com.my.blog.auth.service.AuthService;
import com.my.blog.auth.vo.LoginRequest;
import com.my.blog.support.controller.RestDocsTestSupport;
import com.my.blog.global.common.exception.CommonException;
import com.my.blog.global.jwt.TokenProvider;
import com.my.blog.global.jwt.dto.TokenDTO;
import com.my.blog.global.jwt.error.JWTErrorCode;
import com.my.blog.member.controller.ModelMapper;
import com.my.blog.member.dto.request.CreateRequest;
import com.my.blog.member.entity.vo.*;
import com.my.blog.member.service.MemberService;
import com.my.blog.member.service.dto.MemberDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import javax.servlet.http.Cookie;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerTest extends RestDocsTestSupport {

    @MockBean
    private AuthService authService;

    @Autowired
    MemberService memberService;

    @Autowired
    ModelMapper mapper;

    @Autowired
    private TokenProvider tokenProvider;

    public static final String REFRESH_TOKEN = "refreshToken";
    private static final String BASE_DOCUMENT = "/public/auth/login";
    private static final String BASE_API = "/" + BASE_DOCUMENT;


    @BeforeTestClass
    public void init(){
        CreateRequest request = CreateRequest.builder()
                .email(Email.from("dd@gmail.com"))
                .password(Password.from("passwordDecoded"))
                .name(Name.from("seungeun"))
                .nickName(NickName.from("dd"))
                .roleType(RoleType.USER)
                .build();

        MemberDTO memberDTO = mapper.createMember(request);
        given(memberService.findById(1L)).willReturn(memberDTO);

    }

    @Nested
    class Login{
        @Test
        @DisplayName("로그인")
        public void login() throws Exception {
            Email email = Email.from("dd@gmail.com");
            Password password = Password.from("passwordDecoded");
            LoginRequest loginRequest = LoginRequest.builder()
                    .email(email)
                    .password(password)
                    .build();
            TokenDTO tokenDTO = tokenProvider.generate(email.getEmail(), List.of(RoleType.USER.name()));
            given(authService.login(email,password)).willReturn(tokenDTO);

            mockMvc.perform(post("/api/public/auth/login")
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.accessToken").value(tokenDTO.getAccessToken().getToken()))
                    .andExpect(cookie().value("refreshToken",tokenDTO.getRefreshToken().getToken()))
                    .andDo(restDocs.document(
                                    requestHeaders(headerWithName(HttpHeaders.CONTENT_TYPE).description(APPLICATION_JSON)),
                                    requestFields(
                                            fieldWithPath("email").description("이메일").type(JsonFieldType.STRING),
                                            fieldWithPath("password").description("비밀번호").type(JsonFieldType.STRING)
                                    ),
                                    responseHeaders(
                                            headerWithName(HttpHeaders.CONTENT_TYPE).description(APPLICATION_JSON),
                                            headerWithName(HttpHeaders.SET_COOKIE).description("리프레시 토큰")
                                    ),
                                    responseFields(
                                            fieldWithPath("accessToken").description("액세스토큰").type(JsonFieldType.STRING)
                                    )
                            )
                    );
        }

        @Test
        @DisplayName("잘못된 로그인 정보")
        public void noMember(){

        }

    }

    @Test
    @DisplayName("리프레시 토큰 발행 요청")
    public void refresh() throws Exception {
        Email email = Email.from("dd@gmail.com");
        Password password = Password.from("passwordDecoded");

        TokenDTO tokenDTO = tokenProvider.generate(email.getEmail(), List.of(RoleType.USER.name()));
        given(authService.issueAccessToken(tokenDTO.getRefreshToken().getToken())).willReturn(tokenDTO.getAccessToken());
        Cookie cookie = new Cookie(REFRESH_TOKEN, tokenDTO.getRefreshToken().getToken());
        mockMvc.perform(post("/api/public/auth/refresh")
                        .cookie(cookie)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(tokenDTO.getAccessToken().getToken()));

    }

    @Test
    @DisplayName("만료된 리프레시 토큰 발행 요청")
    public void expiredRefreshToken() throws Exception {
        Email email = Email.from("dd@gmail.com");
        Password password = Password.from("passwordDecoded");
        TokenDTO tokenDTO = tokenProvider.generate(email.getEmail(), List.of(RoleType.USER.name()));

        CommonException exception = new CommonException(JWTErrorCode.EXPIRED_TOKEN);
        given(authService.issueAccessToken(tokenDTO.getRefreshToken().getToken())).willThrow(exception);

        Cookie cookie = new Cookie(REFRESH_TOKEN, tokenDTO.getRefreshToken().getToken());
        mockMvc.perform(post("/api/public/auth/refresh")
                        .cookie(cookie)
                ).
                andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value(exception.getMessage()));


    }

}