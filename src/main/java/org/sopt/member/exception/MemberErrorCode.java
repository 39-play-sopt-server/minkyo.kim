package org.sopt.member.exception;

import org.sopt.common.exception.code.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * 회원 도메인에서 발생하는 오류와 사용자 메시지를 정의합니다.
 */
public enum MemberErrorCode implements ErrorCode {
    NICKNAME_REQUIRED(HttpStatus.BAD_REQUEST, "닉네임은 필수 입력 항목입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."),
    NICKNAME_DUPLICATED(HttpStatus.CONFLICT, "이미 사용 중인 닉네임입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    MemberErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.name();
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
