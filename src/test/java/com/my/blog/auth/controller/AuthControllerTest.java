package com.my.blog.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.blog.auth.service.AuthService;
import com.my.blog.auth.vo.LoginRequest;
import com.my.blog.global.jwt.TokenProvider;
import com.my.blog.global.jwt.dto.TokenDTO;
import com.my.blog.global.security.provider.JwtAuthenticationProvider;
import com.my.blog.member.controller.ModelMapper;
import com.my.blog.member.dto.request.CreateRequest;
import com.my.blog.member.entity.vo.*;
import com.my.blog.member.service.MemberService;
import com.my.blog.member.service.dto.MemberDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class AuthControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    MemberService memberService;

    @Autowired
    ModelMapper mapper;
    public static final String REFRESH_TOKEN = "refreshToken";
    private static final String BASE_DOCUMENT = "/public/auth/login";
    private static final String BASE_API = "/" + BASE_DOCUMENT;

    @MockBean
    private AuthService authService;

    @Autowired
    private TokenProvider tokenProvider;

    @BeforeEach
    public void creatMember(){
        CreateRequest request = CreateRequest.builder()
                .email(Email.from("dd@gmail.com"))
                .password(Password.from("passwordDecoded"))
                .name(Name.from("seungeun"))
                .nickName(NickName.from("dd"))
                .roleType(RoleType.USER)
                .build();

        MemberDTO memberDTO = mapper.createMember(request);
        Long savedId = memberService.save(memberDTO);
    }
    @Test
    public void login() throws Exception {
        Email email = Email.from("dd@gmail.com");
        Password password = Password.from("passwordDecoded");
        LoginRequest loginRequest = LoginRequest.builder()
                .email(email)
                .password(password)
                .build();
        TokenDTO tokenDTO = tokenProvider.generate(email.getEmail(), List.of(RoleType.USER.name()));
        given(authService.login(email,password)).willReturn(tokenDTO);

        mockMvc.perform(post("api/public/auth/login")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.accessToken").value(tokenDTO.getAccessToken()));

    }

}