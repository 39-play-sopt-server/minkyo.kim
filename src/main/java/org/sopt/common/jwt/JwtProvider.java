package org.sopt.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
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
        return createTokenBuilder(
                memberId,
                TokenType.ACCESS,
                accessTokenExpiration
        )
                .compact();
    }

    public String createRefreshToken(Long memberId) {
        return createTokenBuilder(
                memberId,
                TokenType.REFRESH,
                refreshTokenExpiration
        )
                .id(UUID.randomUUID().toString())
                .compact();
    }

    public Long getMemberIdFromAccessToken(String token) {
        return getMemberId(token, TokenType.ACCESS);
    }

    public Long getMemberIdFromRefreshToken(String token) {
        return getMemberId(token, TokenType.REFRESH);
    }

    private Long getMemberId(
            String token,
            TokenType expectedTokenType
    ) {
        Claims claims = parseClaims(token);
        validateTokenType(claims, expectedTokenType);

        return Long.valueOf(claims.getSubject());
    }

    private JwtBuilder createTokenBuilder(
            Long memberId,
            TokenType tokenType,
            long expiration
    ) {
        Date now = new Date();

        return Jwts.builder()
                .subject(String.valueOf(memberId))
                .claim(JwtConstants.TOKEN_TYPE_CLAIM, tokenType.getClaimValue())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expiration))
                .signWith(secretKey);
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private void validateTokenType(
            Claims claims,
            TokenType expectedTokenType
    ) {
        String tokenType = claims.get(
                JwtConstants.TOKEN_TYPE_CLAIM,
                String.class
        );

        if (!expectedTokenType.getClaimValue().equals(tokenType)) {
            throw new JwtException("유효하지 않은 토큰 종류입니다.");
        }
    }

    private enum TokenType {
        ACCESS("access"),
        REFRESH("refresh");

        private final String claimValue;

        TokenType(String claimValue) {
            this.claimValue = claimValue;
        }

        private String getClaimValue() {
            return claimValue;
        }
    }
}
