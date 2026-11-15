package org.sopt.auth.dto;

public record SignupRequest(
        String email,
        String password,
        String nickname
) {
}
