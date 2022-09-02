package com.my.blog.global.security.provider;

import com.my.blog.global.common.exception.CommonException;
import com.my.blog.global.common.utils.StringUtil;
import com.my.blog.global.security.CustomUserDetails;
import com.my.blog.global.security.CustomUserDetailsService;
import com.my.blog.member.error.MemberErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = StringUtil.convertToStr(authentication.getPrincipal(),"");
        String password = StringUtil.convertToStr(authentication.getCredentials(), "");
        UserDetails userDetails = userService.loadUserByUsername(email);

        validatePassword(password, userDetails.getPassword());

        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                null,
                userDetails.getAuthorities());
    }

    private void validatePassword(final String rawPassword, final String encodedPassword){
        if( ! passwordEncoder.matches(rawPassword, encodedPassword) )
            throw new CommonException(MemberErrorCode.INVALID_PASSWORD);
    }

    /**
     * Token 타입에 따른 Provider 지정
     * */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
