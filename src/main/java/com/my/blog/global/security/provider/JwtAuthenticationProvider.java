package com.my.blog.global.security.provider;

import com.my.blog.global.common.utils.StringUtil;
import com.my.blog.global.jwt.TokenProvider;
import com.my.blog.global.security.CustomUserDetails;
import com.my.blog.global.security.authentication.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final TokenProvider tokenProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String accessToken = StringUtil.convertToStr(authentication.getPrincipal(),"");
        tokenProvider.validateToken(accessToken);

        return new UsernamePasswordAuthenticationToken(
                tokenProvider.getIssuer(accessToken),
                null,
                CustomUserDetails.getAuthorities(tokenProvider.getRoles(accessToken))
        );

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtAuthenticationToken.class);
    }
}
