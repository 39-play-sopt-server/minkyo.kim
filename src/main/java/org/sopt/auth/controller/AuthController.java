package org.sopt.auth.controller;

import org.sopt.auth.dto.LoginRequest;
import org.sopt.auth.dto.LoginResponse;
import org.sopt.auth.dto.SignupRequest;
import org.sopt.auth.service.AuthService;
import org.sopt.common.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthApiDocs {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
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
    public ResponseEntity<CommonResponse<LoginResponse>> login(
            @RequestBody LoginRequest request
    ) {
        String accessToken = authService.login(request.email(), request.password());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(
                        "로그인에 성공했습니다.",
                        new LoginResponse(accessToken)
                ));
    }
}
