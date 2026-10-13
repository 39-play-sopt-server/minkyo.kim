package org.sopt.common.code;

import org.springframework.http.HttpStatus;

public interface ResponseCode {
    String getCode();
    String getMessage();
    HttpStatus getHttpStatus();
}
