package org.sopt.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.auth.dto.LoginRequest;
import org.sopt.auth.dto.SignupRequest;
import org.sopt.common.jwt.JwtConstants;
import org.sopt.common.response.CommonResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

/**
 * 인증 API의 요청 조건과 응답 상황을 문서화합니다.
 */
@Tag(name = "인증 API")
public interface AuthApiDocs {

    @Operation(summary = "회원가입")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "409", description = "이미 가입된 이메일인 경우")
    })
    ResponseEntity<CommonResponse<Long>> signup(
            SignupRequest request
    );

    @Operation(summary = "로그인")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공",
                    headers = {
                            @Header(name = HttpHeaders.AUTHORIZATION, description = "Bearer Access Token"),
                            @Header(name = HttpHeaders.SET_COOKIE, description = "HttpOnly Refresh Token 쿠키")
                    }
            ),
            @ApiResponse(responseCode = "401", description = "이메일 또는 비밀번호가 올바르지 않은 경우")
    })
    ResponseEntity<CommonResponse<Void>> login(
            LoginRequest request
    );

    @Operation(summary = "Access Token 재발급")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Access Token 재발급 성공",
                    headers = {
                            @Header(name = HttpHeaders.AUTHORIZATION, description = "새 Bearer Access Token"),
                            @Header(name = HttpHeaders.SET_COOKIE, description = "새 HttpOnly Refresh Token 쿠키")
                    }
            ),
            @ApiResponse(responseCode = "401", description = "Refresh Token이 없거나 유효하지 않은 경우")
    })
    ResponseEntity<CommonResponse<Void>> reissue(
            @Parameter(
                    name = JwtConstants.REFRESH_TOKEN_COOKIE_NAME,
                    description = "로그인 시 발급된 Refresh Token 쿠키",
                    in = ParameterIn.COOKIE,
                    required = true
            )
            String refreshToken
    );

    @Operation(summary = "로그아웃")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그아웃 성공",
                    headers = @Header(
                            name = HttpHeaders.SET_COOKIE,
                            description = "만료된 Refresh Token 쿠키"
                    )
            )
    })
    ResponseEntity<CommonResponse<Void>> logout();
}
