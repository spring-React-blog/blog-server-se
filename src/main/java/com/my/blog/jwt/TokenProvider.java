package com.my.blog.jwt;

import com.my.blog.common.exception.CommonException;
import com.my.blog.member.error.MemberErrorCode;
import com.my.blog.jwt.dto.AccessToken;
import com.my.blog.jwt.dto.RefreshToken;
import com.my.blog.jwt.dto.TokenDTO;
import com.my.blog.member.entity.Member;
import com.my.blog.member.repository.MemberRepository;
import com.my.blog.member.entity.vo.Email;
import com.my.blog.security.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
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

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@Slf4j
@Component
public class TokenProvider implements InitializingBean {
    private static final String AUTHORITIES_KEY = "role";

    private final String secret;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;
    private final MemberRepository memberRepository;

    private Key key;

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.accessToken-validity-in-seconds}") long accessTokenValidityInMilliseconds,
            @Value("${jwt.refreshToken-validity-in-seconds}") long refreshTokenValidityInMilliseconds,
            MemberRepository memberRepository) {
        this.secret = secret;
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds * 1000;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds * 1000;
        this.memberRepository = memberRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDTO createToken(String email, Authentication authentication){
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(joining(","));

        long time = new Date().getTime();
        Member member = memberRepository.findByEmail(Email.from(email)).orElseThrow(()->new CommonException(MemberErrorCode.USER_NOT_FOUND));
        String actoken = createToken(member.getId(), authorities, new Date(time+accessTokenValidityInMilliseconds));
        String retoken = createToken(member.getId(), authorities, new Date(time+refreshTokenValidityInMilliseconds));

        AccessToken accessToken = new AccessToken(actoken);
        RefreshToken refreshToken = new RefreshToken(retoken);
        return new TokenDTO(accessToken, refreshToken);
    }

    public String createToken(Long id,String authorities,Date time){
        return Jwts.builder()
                .claim("id", id)
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(time)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication getAuthentication(String token){
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<? extends SimpleGrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(toList());

        String id = String.valueOf(claims.get("id"));

        Member member = memberRepository.findById(Long.valueOf(id)).orElseThrow(() -> new CommonException(MemberErrorCode.USER_NOT_FOUND));
        CustomUserDetails customUserDetails = new CustomUserDetails(member);

        return new UsernamePasswordAuthenticationToken(customUserDetails,"password",authorities);

    }

    public boolean validateToken(String token){
        try {
            Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;

    }


}
