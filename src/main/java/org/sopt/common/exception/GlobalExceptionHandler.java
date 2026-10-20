package org.sopt.common.exception;

import org.sopt.common.code.CommonErrorCode;
import org.sopt.common.code.ErrorCode;
import org.sopt.common.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 애플리케이션에서 발생한 예외를 공통 API 오류 응답으로 변환합니다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 애플리케이션 예외에 포함된 오류 정보로 실패 응답을 생성합니다.
     *
     * @param e 처리할 사용자 정의 예외
     * @return 오류 상태와 공통 본문을 포함한 HTTP 응답
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResponse<Void>> handleCustomException(CustomException e) {
        ErrorCode code = e.getErrorCode();

        return createErrorResponse(code);
    }

    /**
     * 요청 파라미터를 필요한 타입으로 변환하지 못한 경우 실패 응답을 생성합니다.
     *
     * @param e 요청 파라미터 타입 불일치 예외
     * @return 400 Bad Request와 공통 본문을 포함한 HTTP 응답
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CommonResponse<Void>> handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        ErrorCode code = CommonErrorCode.INVALID_PARAMETER;

        return createErrorResponse(code);
    }

    /**
     * JSON 파싱에 실패한 경우 실패 응답을 생성합니다.
     *
     * @param e JSON 파싱 실패 예외
     * @return 400 Bad Request와 공통 본문을 포함한 HTTP 응답
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CommonResponse<Void>> handleHttpMessageException(HttpMessageNotReadableException e) {
        ErrorCode code = CommonErrorCode.INVALID_PARAMETER;

        return createErrorResponse(code);
    }

    /**
     * 예상하지 못한 예외가 발생한 경우 실패 응답을 생성합니다.
     *
     * @param e 처리할 예상치 못한 예외
     * @return 500 Internal Server Error와 공통 본문을 포함한 HTTP 응답
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Void>> handleUnexpectedException(Exception e) {
        ErrorCode code = CommonErrorCode.INTERNAL_SERVER_ERROR;

        return createErrorResponse(code);
    }

    private ResponseEntity<CommonResponse<Void>> createErrorResponse(
            ErrorCode errorCode
    ) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(CommonResponse.failure(errorCode, null));
    }
}
