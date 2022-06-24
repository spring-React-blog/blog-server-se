package com.my.blog.global.jwt;

import com.my.blog.global.common.exception.CommonException;
import com.my.blog.global.jwt.dto.RefreshToken;
import com.my.blog.global.jwt.error.JWTErrorCode;
import com.my.blog.global.security.dto.LoginAuth;
import com.my.blog.member.entity.vo.RoleType;
import com.my.blog.member.error.MemberErrorCode;
import com.my.blog.global.jwt.dto.AccessToken;
import com.my.blog.global.jwt.dto.TokenDTO;
import com.my.blog.member.entity.Member;
import com.my.blog.member.repository.MemberRepository;
import com.my.blog.member.entity.vo.Email;
import com.my.blog.global.security.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {
    private static final String AUTHORITIES_KEY = "roles";
    private static final String DELIMITER = ", ";

    private final TokenProperties properties;
    private final MemberRepository memberRepository;


    public TokenDTO generate(String email, List<String> roles) {

        Date today = new Date();
        Member member = memberRepository.findByEmail(Email.from(email)).orElseThrow(() -> new CommonException(MemberErrorCode.USER_NOT_FOUND));

        AccessToken accessToken = new AccessToken(createToken(member.email(), roles, properties.getAccessTokenExpiredDate(today)));
        RefreshToken refreshToken = new RefreshToken(createToken(member.email(), roles, properties.getRefreshTokenExpiredDate(today)));

        return new TokenDTO(accessToken, refreshToken);
    }

    public String createToken(String email, List<String> authorities, Date time) {
        return Jwts.builder()
                .setIssuer(email)
                .claim(AUTHORITIES_KEY, String.join(DELIMITER, authorities))
                .setExpiration(time)
                .signWith(properties.getKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(properties.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<? extends SimpleGrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(toList());

        String email = String.valueOf(claims.get("email"));

        LoginAuth loginAuth = memberRepository.findByLoginEmail(Email.from(email)).orElseThrow(() -> new CommonException(MemberErrorCode.USER_NOT_FOUND));
        CustomUserDetails customUserDetails = CustomUserDetails.from(loginAuth);

        return new UsernamePasswordAuthenticationToken(customUserDetails, "password", authorities);

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
    public boolean validateToken(String token) {
        try {
            Jwts
                    .parserBuilder()
                    .setSigningKey(properties.getKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
            throw new CommonException(JWTErrorCode.INVALID_SIGNITURE);

        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw new CommonException(JWTErrorCode.MALFORMED_TOKEN);

        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw new CommonException(JWTErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw new CommonException(JWTErrorCode.UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw new CommonException(JWTErrorCode.INVALID_CLAIMS);
        }


    }



}

