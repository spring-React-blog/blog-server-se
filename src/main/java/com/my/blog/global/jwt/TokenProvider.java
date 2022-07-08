package com.my.blog.global.jwt;

import com.my.blog.global.common.exception.CommonException;
import com.my.blog.global.jwt.dto.RefreshToken;
import com.my.blog.global.jwt.error.JWTErrorCode;
import com.my.blog.global.jwt.dto.AccessToken;
import com.my.blog.global.jwt.dto.TokenDTO;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Component
public class TokenProvider {
    private static final String AUTHORITIES_KEY = "roles";
    private static final String DELIMITER = ", ";

    private final TokenProperties properties;

    public TokenDTO generate(String email, List<String> roles) {
        AccessToken accessToken = generateAccessToken(email, roles );
        RefreshToken refreshToken = generateRefreshToken(email, roles );

        return new TokenDTO(accessToken, refreshToken);
    }

    public AccessToken generateAccessToken(String email, List<String> roles) {
        String token = Jwts.builder()
                .setIssuer(email)
                .claim(AUTHORITIES_KEY, String.join(DELIMITER, roles))
                .setExpiration(properties.getAccessTokenExpiredDate(new Date()))
                .signWith(properties.getKey(), SignatureAlgorithm.HS512)
                .compact();
        return new AccessToken(token);
    }

    public RefreshToken generateRefreshToken(String email, List<String> roles) {
        String token = Jwts.builder()
                .setIssuer(email)
                .claim(AUTHORITIES_KEY, String.join(DELIMITER, roles))
                .setExpiration(properties.getRefreshTokenExpiredDate(new Date()))
                .signWith(properties.getKey(), SignatureAlgorithm.HS512)
                .compact();
        return new RefreshToken(token);
    }

    public String getIssuer(String token) {
        return getClaims(token).getIssuer();
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(properties.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public List<String> getRoles(String token) {
        String roles = (String) getClaims(token)
                .get(AUTHORITIES_KEY);
        if(Objects.isNull(roles)) return List.of();
        return Arrays.stream(roles.split(DELIMITER)).collect(toList());
    }

    public Date getExpirationDate(String token){
        return getClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token){
        Date expirationDate = getExpirationDate(token);
        return expirationDate.before(new Date());
    }

    public boolean validateToken(String token) {
        if(isTokenExpired(token)) throw new CommonException(JWTErrorCode.EXPIRED_TOKEN);
        if(getRoles(token).equals(List.of())) throw new CommonException(JWTErrorCode.INVALID_TOKEN);
        return true;
    }



}

