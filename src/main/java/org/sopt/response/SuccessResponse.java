package org.sopt.response;

/**
 * 성공 메시지와 처리 결과 데이터를 전달합니다.
 *
 * @param message 성공 메시지
 * @param data 처리 결과 데이터
 * @param <T> 응답 데이터 타입
 */
public record SuccessResponse<T>(
        String message,
        T data
) implements Response<T> {
    @Override
    public boolean success() {
        return true;
    }
}