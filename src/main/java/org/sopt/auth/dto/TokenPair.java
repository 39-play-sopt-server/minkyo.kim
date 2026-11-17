package org.sopt.auth.dto;

/**
 * Access Token과 Refresh Token 쌍입니다.
 */
public record TokenPair(
        String accessToken,
        String refreshToken
) {
}
