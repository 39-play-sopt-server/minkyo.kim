package org.sopt.response;

public interface Response<T> {
    boolean success();
    String message();
    T data();
}
