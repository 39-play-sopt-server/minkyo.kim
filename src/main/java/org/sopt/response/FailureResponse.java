package org.sopt.response;

/**
 * 실패 메시지를 전달합니다.
 *
 * @param message 실패 메시지
 * @param <T> 요청이 성공했을 때 반환될 데이터 타입
 */
public record FailureResponse<T>(
        String message
) implements Response<T> {
    @Override
    public boolean success() {
        return false;
    }

    @Override
    public T data() {
        return null;
    }
}