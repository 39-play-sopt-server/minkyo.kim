package org.sopt.common.exception;

import org.sopt.common.code.ErrorCode;
import org.sopt.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 애플리케이션에서 발생한 예외를 공통 API 오류 응답으로 변환합니다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 도메인 규칙 위반에 포함된 오류 정보로 실패 응답을 생성합니다.
     *
     * @param e 처리할 사용자 정의 예외
     * @return 오류 상태와 공통 본문을 포함한 HTTP 응답
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
        ErrorCode code = e.getErrorCode();

        return ResponseEntity
                .status(code.getHttpStatus())
                .body(ApiResponse.of(code, null));
    }
}
