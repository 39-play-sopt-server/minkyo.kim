package org.sopt.common;

import org.sopt.common.code.ErrorCode;
import org.sopt.common.exception.CustomException;

public record Pagination(
        int page,
        int size
) {
    public Pagination {
        if (page < 0) {
            throw new CustomException(ErrorCode.INVALID_PAGE);
        }
        if (size <= 0 || size > 50) {
            throw new CustomException(ErrorCode.INVALID_PAGE_SIZE);
        }
    }

    public long offset() {
        return (long) page * size;
    }
}
