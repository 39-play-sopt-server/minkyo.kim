package org.sopt.common.jwt;

/**
 * JWT와 HTTP 토큰 교환에서 공통으로 사용하는 상수입니다.
 */
public final class JwtConstants {

    public static final String TOKEN_TYPE_CLAIM = "token_type";
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final String BEARER_PREFIX = "Bearer ";

    private JwtConstants() {
    }
}
