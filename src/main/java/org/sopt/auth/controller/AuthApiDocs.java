package org.sopt.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.auth.dto.LoginRequest;
import org.sopt.auth.dto.LoginResponse;
import org.sopt.auth.dto.SignupRequest;
import org.sopt.common.response.CommonResponse;
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
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "이메일 또는 비밀번호가 올바르지 않은 경우")
    })
    ResponseEntity<CommonResponse<LoginResponse>> login(
            LoginRequest request
    );
}
