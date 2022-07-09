package com.my.blog.auth.service;

import com.my.blog.global.common.exception.CommonException;
import com.my.blog.global.common.utils.StringUtil;
import com.my.blog.global.jwt.TokenProvider;
import com.my.blog.global.jwt.dto.AccessToken;
import com.my.blog.global.jwt.dto.TokenDTO;
import com.my.blog.global.security.CustomUserDetails;
import com.my.blog.global.security.dto.LoginAuth;
import com.my.blog.member.entity.vo.Email;
import com.my.blog.member.entity.vo.Password;
import com.my.blog.member.error.MemberErrorCode;
import com.my.blog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;


    public TokenDTO login(final Email email, final Password password){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email.getEmail(), password.getPassword()));

        List<String> roles = authenticate.getAuthorities().stream()
                .map(auth -> auth.getAuthority().replaceAll(CustomUserDetails.ROLE, ""))
                .collect(toList());


        return tokenProvider.generate(StringUtil.convertToStr(authenticate.getPrincipal(),""),roles);
    }

    public AccessToken issueAccessToken(final String refreshToken) {
        tokenProvider.validateToken(refreshToken);
        String email = tokenProvider.getIssuer(refreshToken);
        LoginAuth loginAuth = memberRepository.findByLoginEmail(Email.from(email)).orElseThrow(() -> new CommonException(MemberErrorCode.USER_NOT_FOUND));

        return tokenProvider.generateAccessToken(email, List.of(loginAuth.getRoleType().name()));
    }
}
