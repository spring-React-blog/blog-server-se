package com.my.blog.global.config;

import com.my.blog.global.common.exception.FilterExceptionHandler;
import com.my.blog.global.jwt.handler.JwtAccessDeniedHandler;
import com.my.blog.global.jwt.handler.JwtAuthenticateFilter;
import com.my.blog.global.jwt.handler.JwtAuthenticationEntryPoint;
import com.my.blog.global.security.provider.JwtAuthenticationProvider;
import com.my.blog.global.security.provider.LoginAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  {
    private static final String PUBLIC = "/api/public/**";
    private static final String DB = "/console/**";

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final LoginAuthenticationProvider loginAuthenticationProvider;
    private final JwtAuthenticationProvider jwtAuthorizationProvider;

    private JwtAuthenticateFilter jwtAuthenticateFilter;
    private FilterExceptionHandler filterExceptionHandler;

   @Bean
    public FilterExceptionHandler filterExceptionHandler(){
        return new FilterExceptionHandler();
    }
    @Bean
    public JwtAuthenticateFilter jwtAuthenticateFilter() throws Exception {
        return new JwtAuthenticateFilter(authenticationManager());
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        authenticationManagerBuilder.authenticationProvider(loginAuthenticationProvider);
        authenticationManagerBuilder.authenticationProvider(jwtAuthorizationProvider);
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable();

        http
                .cors()
                .configurationSource(corsConfigurationSource())// 클라이언트와 다른 port 사용 시 모든 출처 허용
                .and()
                .formLogin().disable() //스프링 기본 login form 미사용
                .authorizeRequests()
                .antMatchers(PUBLIC).permitAll()
                .antMatchers(DB).permitAll()
                .antMatchers("/js/**", "/favicon.ico/**", "/css/**", "/","/console/","/static").permitAll()
                .anyRequest().authenticated()

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint()) // 인증, 허가 에러 시 공통적으로 처리해주는 부분이다.
                .accessDeniedHandler(new JwtAccessDeniedHandler())
                .and()
                .addFilterBefore(jwtAuthenticateFilter(), UsernamePasswordAuthenticationFilter.class )
                .addFilterBefore(filterExceptionHandler() , JwtAuthenticateFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //JWT를 쓰려면 Spring Security에서 기본적으로 지원하는 Session 설정을 해제해야 한다.

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
        return web -> web.ignoring().antMatchers("/js/**", "/favicon.ico/**", "/css/**", "/","/console/","/docs/**");
    }
    /*@Override WebSecurityConfigurerAdapter
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }*/

}
