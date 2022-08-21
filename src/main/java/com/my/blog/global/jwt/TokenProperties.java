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

@ConfigurationProperties("jwt")
@ConstructorBinding
public class TokenProperties {

    private final long accessTokenValidityInSeconds;
    private final long refreshTokenValidityInSeconds;

    private final Key key;

    public TokenProperties(
           final String secret,
           final long accessTokenValidityInSeconds,
           final long refreshTokenValidityInSeconds
            ) {
        this.accessTokenValidityInSeconds = accessTokenValidityInSeconds ;
        this.refreshTokenValidityInSeconds = refreshTokenValidityInSeconds ;
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
