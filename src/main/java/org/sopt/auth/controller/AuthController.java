package org.sopt.auth.controller;

import org.sopt.auth.dto.LoginRequest;
import org.sopt.auth.dto.SignupRequest;
import org.sopt.auth.dto.TokenPair;
import org.sopt.auth.service.AuthService;
import org.sopt.common.jwt.JwtConstants;
import org.sopt.common.response.CommonResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthApiDocs {

    private final AuthService authService;
    private final long refreshTokenExpiration;

    public AuthController(
            AuthService authService,
            @Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration
    ) {
        this.authService = authService;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    @Override
    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<Long>> signup(
            @RequestBody SignupRequest request
    ) {
        Long memberId = authService.signup(
                request.email(),
                request.password(),
                request.nickname()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.success(
                        "회원가입에 성공했습니다.",
                        memberId
                ));
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<CommonResponse<Void>> login(
            @RequestBody LoginRequest request
    ) {
        TokenPair tokenPair = authService.login(
                request.email(),
                request.password()
        );

        return createTokenResponse(tokenPair, "로그인에 성공했습니다.");
    }

    @Override
    @PostMapping("/reissue")
    public ResponseEntity<CommonResponse<Void>> reissue(
            @CookieValue(name = JwtConstants.REFRESH_TOKEN_COOKIE_NAME, required = false) String refreshToken
    ) {
        TokenPair tokenPair = authService.reissue(refreshToken);

        return createTokenResponse(tokenPair, "Access Token이 재발급되었습니다.");
    }

    private ResponseEntity<CommonResponse<Void>> createTokenResponse(
            TokenPair tokenPair,
            String message
    ) {
        ResponseCookie refreshTokenCookie = ResponseCookie
                .from(JwtConstants.REFRESH_TOKEN_COOKIE_NAME, tokenPair.refreshToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path(JwtConstants.REFRESH_TOKEN_COOKIE_PATH)
                .maxAge(Duration.ofMillis(refreshTokenExpiration))
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(
                        HttpHeaders.AUTHORIZATION,
                        JwtConstants.BEARER_PREFIX + tokenPair.accessToken()
                )
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(CommonResponse.success(message, null));
    }
}
