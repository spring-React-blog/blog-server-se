package com.my.blog.global.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final String accessToken;

    private JwtAuthenticationToken(final String accessToken){
        super(null);
        this.accessToken = accessToken;
    }

    public static JwtAuthenticationToken from(final String accessToken){
        return new JwtAuthenticationToken(accessToken);
    }
    @Override
    public Object getPrincipal() {
        return this.accessToken;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

}
