package com.my.blog.global.common.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class FilterExceptionHandler extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        } catch (AuthenticationException authenticationException ) {
            log.debug("authenticationException= {}" , authenticationException);
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, authenticationException);

        } catch (Exception exception) {
            log.debug("exception= {}" , exception);
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, exception);
        }
    }

    private void setErrorResponse(HttpStatus status, HttpServletResponse response, Exception ex) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        log.debug("ex = {}" , ex);

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());

        ObjectMapper mapper = new ObjectMapper();

        String message="";
        try {
            message = mapper.writeValueAsString(errorResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            response.getWriter().write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
