package com.my.blog.global.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.security.Key;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;

@ConfigurationProperties(prefix = "jwt")
@ConstructorBinding
public class TokenProperties {

    private final String secret;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;

    private Key key;

    public TokenProperties(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.accessToken-validity-in-seconds}") long accessTokenValidityInMilliseconds,
            @Value("${jwt.refreshToken-validity-in-seconds}") long refreshTokenValidityInMilliseconds
            ) {
        this.secret = secret;
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds * 1000;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds * 1000;
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public Key getKey(){
        return key;
    }

    public Date getAccessTokenExpiredDate(final Date today){
        return getExpiredDate(today, accessTokenValidityInMilliseconds);
    }
    public Date getRefreshTokenExpiredDate(final Date today){
        return getExpiredDate(today, refreshTokenValidityInMilliseconds);
    }

    public int getRefreshTokenExpiredSeconds(){
        return (int) Duration.ofSeconds(refreshTokenValidityInMilliseconds).toSeconds();
    }
    private Date getExpiredDate(Date today, long expiredSeconds) {
        return new Date(today.getTime() + Duration.ofSeconds(expiredSeconds).toMillis());
    }

}
