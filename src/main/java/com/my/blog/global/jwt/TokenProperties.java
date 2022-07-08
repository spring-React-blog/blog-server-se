package com.my.blog.global.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;

@Component
public class TokenProperties {

    private final long accessTokenValidityInSeconds;
    private final long refreshTokenValidityInSeconds;

    private Key key;

    public TokenProperties(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.accessToken-validity-in-seconds}") long accessTokenValidityInSeconds,
            @Value("${jwt.refreshToken-validity-in-seconds}") long refreshTokenValidityInSeconds
            ) {
        this.accessTokenValidityInSeconds = accessTokenValidityInSeconds * 1000;
        this.refreshTokenValidityInSeconds = refreshTokenValidityInSeconds * 1000;
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public Key getKey(){
        return key;
    }

    public Date getAccessTokenExpiredDate(final Date today){
        return getExpiredDate(today, accessTokenValidityInSeconds);
    }
    public Date getRefreshTokenExpiredDate(final Date today){
        return getExpiredDate(today, refreshTokenValidityInSeconds);
    }

    public int getRefreshTokenExpiredSeconds(){
        return (int) Duration.ofSeconds(refreshTokenValidityInSeconds).toSeconds();
    }
    private Date getExpiredDate(Date today, long expiredSeconds) {
        return new Date(today.getTime() + Duration.ofSeconds(expiredSeconds).toMillis());
    }

}
