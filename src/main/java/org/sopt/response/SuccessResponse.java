package org.sopt.response;

public record SuccessResponse<T> (
        String message,
        T data
) implements Response<T> {
    @Override
    public boolean success() {
        return true;
    }
}
