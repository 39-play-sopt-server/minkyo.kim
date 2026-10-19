package org.sopt.common.code;

import org.springframework.http.HttpStatus;

/**
 * API 요청이 정상적으로 처리된 상황과 사용자 메시지를 정의합니다.
 */
public enum SuccessCode implements ResponseCode {
    POST_CREATED(HttpStatus.CREATED, "게시글이 작성되었습니다."),
    POST_READ(HttpStatus.OK, null),
    POSTS_READ(HttpStatus.OK, null),
    POST_UPDATED(HttpStatus.OK, "게시글이 수정되었습니다."),
    NO_POSTS(HttpStatus.OK, "게시글이 없습니다."),
    POST_DELETED(HttpStatus.OK, "게시글이 삭제되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    SuccessCode(HttpStatus httpStatus, String message) {
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
