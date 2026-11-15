package org.sopt.auth.exception;

import org.sopt.common.exception.code.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * 인증 과정에서 발생할 수 있는 오류를 정의합니다.
 */
public enum AuthErrorCode implements ErrorCode {
    EMAIL_DUPLICATED(HttpStatus.CONFLICT, "이미 가입된 이메일입니다."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다."),
    AUTHENTICATION_REQUIRED(HttpStatus.UNAUTHORIZED, "인증 토큰이 없거나 유효하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    AuthErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
