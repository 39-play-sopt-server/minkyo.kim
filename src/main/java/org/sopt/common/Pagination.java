package org.sopt.common;

import org.sopt.common.code.CommonErrorCode;
import org.sopt.common.exception.CustomException;

/**
 * 페이지 단위 조회에 사용할 페이지 번호와 크기를 나타냅니다.
 *
 * @param page 0부터 시작하는 페이지 번호
 * @param size 한 페이지에서 조회할 항목 수
 */
public record Pagination(
        int page,
        int size
) {
    public Pagination {
        if (page < 0) {
            throw new CustomException(CommonErrorCode.INVALID_PAGE);
        }
        if (size <= 0 || size > 50) {
            throw new CustomException(CommonErrorCode.INVALID_PAGE_SIZE);
        }
    }
}
