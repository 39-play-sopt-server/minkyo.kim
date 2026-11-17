package org.sopt.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtProvider {

    private final SecretKey secretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expiration}") long accessTokenExpiration,
            @Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration
    ) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public String createAccessToken(Long memberId) {
        Date now = new Date();

        return Jwts.builder()
                .subject(String.valueOf(memberId))   // 누구의 토큰인지
                .claim("token_type", "access")
                .issuedAt(now)                       // 발급 시각
                .expiration(new Date(now.getTime() + accessTokenExpiration)) // 만료 시각
                .signWith(secretKey)                 // 서명
                .compact();
    }

    public String createRefreshToken(Long memberId) {
        Date now = new Date();

        return Jwts.builder()
                .subject(String.valueOf(memberId))
                .claim("token_type", "refresh")
                .id(UUID.randomUUID().toString())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + refreshTokenExpiration))
                .signWith(secretKey)
                .compact();
    }

    public Long getMemberIdFromAccessToken(String token) {
        return getMemberId(token, "access");
    }

    public Long getMemberIdFromRefreshToken(String token) {
        return getMemberId(token, "refresh");
    }

    private Long getMemberId(
            String token,
            String expectedTokenType
    ) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)  // 서명, 만료 검증 → 실패 시 JwtException
                .getPayload();

        String tokenType = claims.get("token_type", String.class);
        if (!expectedTokenType.equals(tokenType)) {
            throw new io.jsonwebtoken.JwtException("유효하지 않은 토큰 종류입니다.");
        }

        return Long.valueOf(claims.getSubject());
    }
}
