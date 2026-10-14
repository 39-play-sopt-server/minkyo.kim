package org.sopt.response;

import org.sopt.common.code.ResponseCode;

/**
 * 서버가 클라이언트에 전달하는 공통 응답 형식을 나타냅니다.
 *
 * @param <T> 응답 데이터 타입
 */
public record ApiResponse<T>(
    String code,
    String message,
    T data
){
    /**
     * 응답 코드와 전달할 데이터로 공통 응답을 생성합니다.
     *
     * @param responseCode 응답에 사용할 코드와 메시지
     * @param data 클라이언트에 전달할 데이터
     * @param <T> 응답 데이터 타입
     * @return 코드 정보와 데이터가 반영된 공통 응답
     */
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