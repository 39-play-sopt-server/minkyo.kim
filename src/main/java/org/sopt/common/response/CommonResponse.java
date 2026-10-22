package org.sopt.common.response;

import org.sopt.common.exception.code.ErrorCode;

/**
 * 서버가 클라이언트에 전달하는 공통 응답 형식을 나타냅니다.
 *
 * @param <T> 응답 데이터 타입
 */
public record CommonResponse<T>(
    String code,
    String message,
    T data
){
    public static <T> CommonResponse<T> success(
            String message,
            T data
    ) {
        return new CommonResponse<>(
                "SUCCESS",
                message,
                data
        );
    }
    /**
     * 응답 코드와 전달할 데이터로 공통 응답을 생성합니다.
     *
     * @param errorCode 응답에 사용할 코드와 메시지
     * @param data 클라이언트에 전달할 데이터
     * @param <T> 응답 데이터 타입
     * @return 코드 정보와 데이터가 반영된 공통 응답
     */
    public static <T> CommonResponse<T> failure(
            ErrorCode errorCode,
            T data
    ) {
        return new CommonResponse<>(
                errorCode.getCode(),
                errorCode.getMessage(),
                data
        );
    }
}