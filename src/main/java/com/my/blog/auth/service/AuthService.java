package com.my.blog.auth.service;

import com.my.blog.common.errorcode.JWTErrorCode;
import com.my.blog.common.errorcode.MemberErrorCode;
import com.my.blog.common.exception.CommonException;
import com.my.blog.jwt.TokenProvider;
import com.my.blog.jwt.dto.AccessToken;
import com.my.blog.jwt.dto.TokenDTO;
import com.my.blog.member.repository.MemberRepository;
import com.my.blog.member.vo.Email;
import com.my.blog.member.vo.Password;
import com.my.blog.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder managerBuilder;
    private final TokenProvider tokenProvider;

  /*  public TokenDTO save(Email email, Password password){
        memberRepository.save()
    }*/

    public TokenDTO login(Email email, Password password){
        String usrEmail = email.getEmail();
        String usrPwd = password.getPassword();

        CustomUserDetails userDetails = memberRepository.findByEmail(email)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new CommonException(MemberErrorCode.USER_NOT_FOUND));

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, usrPwd);

        Authentication authenticate = managerBuilder.getObject().authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return tokenProvider.createToken(usrEmail,authenticate);
    }

    public AccessToken reissue(String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new CommonException(JWTErrorCode.INVALID_TOKEN);
        }

        Authentication authentication = tokenProvider.getAuthentication(refreshToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        return tokenProvider.createToken(principal.getUsername(),authentication).getAccessToken();
    }
}
