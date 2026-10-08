package org.sopt.response;

/**
 * 서버가 클라이언트에 전달하는 공통 응답 계약입니다.
 *
 * @param <T> 응답 데이터 타입
 */
public interface Response<T> {
    boolean success();
    String message();
    T data();
}