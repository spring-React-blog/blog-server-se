package com.my.blog.config;

import com.my.blog.jwt.TokenProvider;
import com.my.blog.jwt.handler.JwtAccessDeniedHandler;
import com.my.blog.jwt.handler.JwtAuthenticateFilter;
import com.my.blog.jwt.handler.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  {
    private static final String PUBLIC = "/api/v1/public/**";
    private static final String DB = "/console/**";

    @Autowired
    private final TokenProvider tokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // restapi 라서 불필요
                .cors() // 클라이언트와 다른 port 사용 시 모든 출처 허용
                .and()

                .exceptionHandling()
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint()) // 인증, 허가 에러 시 공통적으로 처리해주는 부분이다.
                .accessDeniedHandler(new JwtAccessDeniedHandler())
                .and()
                .addFilterBefore(new JwtAuthenticateFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //JWT를 쓰려면 Spring Security에서 기본적으로 지원하는 Session 설정을 해제해야 한다.

                .and()
                .formLogin().disable() //스프링 기본 login form 미사용
                .authorizeHttpRequests()
                .antMatchers(PUBLIC).permitAll()
                .antMatchers(DB).permitAll()
                .anyRequest().authenticated()
                .and()
                .headers()
                .frameOptions().sameOrigin()
                ;

        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().antMatchers("/js/**", "/favicon.ico/**", "/css/**", "/","/console/");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
