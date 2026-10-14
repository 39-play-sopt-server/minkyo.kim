package org.sopt.common;

import org.sopt.common.code.ErrorCode;
import org.sopt.common.exception.CustomException;

/**
 * pagination에 사용할 페이지 번호와 크기를 나타냅니다.
 *
 * @param page 0부터 시작하는 페이지 번호
 * @param size 한 페이지에서 조회할 게시글 수
 */
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

    /**
     * 특정 페이지 조회를 위해 건너뛸 게시글 수를 계산합니다.
     *
     * @return 조회 시작 위치
     */
    public long offset() {
        return (long) page * size;
    }
}
