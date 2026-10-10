package org.sopt.exception;

/**
 * 서버에서 발생할 수 있는 예외 상황과 사용자 메시지를 정의합니다.
 */
public enum ErrorCode {
    POST_NOT_FOUND("존재하지 않는 게시글입니다."),
    CATEGORY_NOT_FOUND("존재하지 않는 카테고리입니다."),
    TITLE_REQUIRED("제목은 필수 입력 항목입니다."),
    CONTENT_REQUIRED("내용은 필수 입력 항목입니다."),
    CATEGORY_REQUIRED("카테고리는 필수 입력 항목입니다."),
    WRITER_REQUIRED("작성자는 필수 입력 항목입니다.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}