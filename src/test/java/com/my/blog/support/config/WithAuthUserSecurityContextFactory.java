package com.my.blog.support.config;

import com.my.blog.global.security.CustomUserDetails;
import com.my.blog.global.security.dto.LoginAuth;
import com.my.blog.member.entity.vo.Email;
import com.my.blog.member.entity.vo.Password;
import com.my.blog.member.entity.vo.RoleType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithAuthUserSecurityContextFactory implements WithSecurityContextFactory<AuthUser> {
    @Override
    public SecurityContext createSecurityContext(AuthUser annotation) {
        String email = annotation.username();
        LoginAuth loginAuth = LoginAuth.builder()
                .email(Email.from(email))
                .password(Password.from(annotation.password()))
                .roleType(RoleType.USER)
                .build();
        CustomUserDetails userDetails = CustomUserDetails.from(loginAuth);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                null,
                userDetails.getAuthorities());
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authToken);

        return context;
    }
}
