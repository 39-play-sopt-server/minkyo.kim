package org.sopt.response;

import org.sopt.common.code.ResponseCode;

/**
 * 서버가 클라이언트에 전달하는 공통 응답 계약입니다.
 *
 * @param <T> 응답 데이터 타입
 */
public record ApiResponse<T>(
    String code,
    String message,
    T data
){
    public static <T> ApiResponse<T> of(
            ResponseCode responseCode,
            T data
    ) {
        return new ApiResponse<>(
                responseCode.getCode(),
                responseCode.getMessage(),
                data
        );
    }
}