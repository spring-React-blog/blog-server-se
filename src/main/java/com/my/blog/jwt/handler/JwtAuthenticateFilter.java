package com.my.blog.jwt.handler;

import com.my.blog.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticateFilter extends GenericFilterBean {
    private final String JWT_REQUEST_HEADER = "Authorization";
    private final String JWT_TOKEN_HEADER = "Bearer ";
    private final TokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorization = ((HttpServletRequest) request).getHeader(JWT_REQUEST_HEADER);
        String jwtToken = getJwtToken(authorization);
        if(jwtToken!=null && tokenProvider.validateToken(jwtToken)){
            Authentication authentication = tokenProvider.getAuthentication(jwtToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request,response);
    }

    private String getJwtToken(String authorization) {
        if(StringUtils.hasText(authorization) && authorization.startsWith(JWT_TOKEN_HEADER)){
            return authorization.substring(JWT_TOKEN_HEADER.length());
        }
        return null;
    }
}
