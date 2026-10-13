package org.sopt.common.exception;

import org.sopt.common.code.ErrorCode;

/**
 * 서버의 규칙 위반을 호출 계층에 전달하는 공통 예외입니다.
 */
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}