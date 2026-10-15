package org.sopt.common.code;

import org.springframework.http.HttpStatus;

/**
 * 서버에서 발생할 수 있는 예외 상황과 사용자 메시지를 정의합니다.
 */
public enum ErrorCode implements ResponseCode {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."),
    INVALID_CATEGORY(HttpStatus.BAD_REQUEST, "존재하지 않는 카테고리입니다."),
    TITLE_REQUIRED(HttpStatus.BAD_REQUEST, "제목은 필수 입력 항목입니다."),
    CONTENT_REQUIRED(HttpStatus.BAD_REQUEST, "내용은 필수 입력 항목입니다."),
    CATEGORY_REQUIRED(HttpStatus.BAD_REQUEST, "카테고리는 필수 입력 항목입니다."),
    WRITER_REQUIRED(HttpStatus.BAD_REQUEST, "작성자는 필수 입력 항목입니다."),
    INVALID_PAGE(HttpStatus.BAD_REQUEST, "page 값은 0 이상이어야 합니다."),
    INVALID_PAGE_SIZE(HttpStatus.BAD_REQUEST, "size 값은 1 이상, 50 이하여야 합니다."),

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "요청 파라미터의 형식이 올바르지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
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