package org.sopt.common.exception;

import org.sopt.common.code.ErrorCode;
import org.sopt.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
        ErrorCode code = e.getErrorCode();

        return ResponseEntity
                .status(code.getHttpStatus())
                .body(ApiResponse.of(code, null));
    }
}
