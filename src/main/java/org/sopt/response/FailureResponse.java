package org.sopt.response;

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
