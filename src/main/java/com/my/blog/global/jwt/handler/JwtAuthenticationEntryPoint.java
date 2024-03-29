package com.my.blog.global.jwt.handler;


import com.my.blog.global.common.errorcode.ErrorCode;
import com.my.blog.global.jwt.error.JWTErrorCode;
import com.my.blog.member.error.MemberErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request
            , HttpServletResponse response
            , AuthenticationException authException) throws IOException, ServletException {

        String exception = (String)request.getAttribute("exception");
        ErrorCode errorCode;

        log.info("log: exception: {} ", exception);
        log.info("log: authException: {} ", authException);
        log.info("log: authException.getMessage: {} ", authException.getMessage());

       if(exception == null) {
            errorCode = MemberErrorCode.NON_LOGIN;
            setResponse(response,  errorCode);
            return;
        }

        if(exception.equals(JWTErrorCode.EXPIRED_TOKEN.getStatus())) {
            errorCode = JWTErrorCode.EXPIRED_TOKEN;
            setResponse(response, errorCode);
            return;
        }

        if(exception.equals(JWTErrorCode.INVALID_TOKEN.getStatus())) {
            errorCode = JWTErrorCode.INVALID_TOKEN;
            setResponse(response, errorCode);
        }
    }
    /**
     * 한글 출력을 위해 getWriter() 사용
     */
    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println("{ \"message\" : \"" + errorCode.getMessage()
                + "\", \"status\" : " + errorCode.getStatus()
                + ", \"errors\" : [ ] }");
    }

}

