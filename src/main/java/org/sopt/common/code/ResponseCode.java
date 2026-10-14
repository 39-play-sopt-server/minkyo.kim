package org.sopt.common.code;

import org.springframework.http.HttpStatus;

/**
 * API 응답 코드가 제공해야 할 공통 형식입니다.
 */
public interface ResponseCode {
    String getCode();
    String getMessage();
    HttpStatus getHttpStatus();
}
