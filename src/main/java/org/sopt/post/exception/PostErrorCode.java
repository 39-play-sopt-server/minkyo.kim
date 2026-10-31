package org.sopt.post.exception;

import org.sopt.common.exception.code.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * 게시글 도메인에서 발생하는 오류와 사용자 메시지를 정의합니다.
 */
public enum PostErrorCode implements ErrorCode {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."),
    INVALID_CATEGORY(HttpStatus.BAD_REQUEST, "존재하지 않는 카테고리입니다."),
    TITLE_REQUIRED(HttpStatus.BAD_REQUEST, "제목은 필수 입력 항목입니다."),
    CONTENT_REQUIRED(HttpStatus.BAD_REQUEST, "내용은 필수 입력 항목입니다."),
    CATEGORY_REQUIRED(HttpStatus.BAD_REQUEST, "카테고리는 필수 입력 항목입니다."),
    WRITER_REQUIRED(HttpStatus.BAD_REQUEST, "작성자는 필수 입력 항목입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    PostErrorCode(HttpStatus httpStatus, String message) {
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