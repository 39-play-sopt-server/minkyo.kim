package org.sopt.like.exception;

import org.sopt.common.exception.code.ErrorCode;
import org.springframework.http.HttpStatus;

public enum PostLikeErrorCode implements ErrorCode {
    LIKE_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 좋아요를 누른 게시글입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    PostLikeErrorCode(HttpStatus httpStatus, String message) {
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
