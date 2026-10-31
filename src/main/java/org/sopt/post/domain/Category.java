package org.sopt.post.domain;

import org.sopt.common.exception.CustomException;
import org.sopt.post.exception.PostErrorCode;

/**
 * 게시글이 속할 수 있는 카테고리와 사용자에게 표시할 이름을 정의합니다.
 */
public enum Category {
    NOTICE("공지"),
    QUESTION("질문"),
    GENERAL("자유"),
    INFORMATION("정보");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return this.displayName;
    }

    /**
     * 입력 문자열에 해당하는 카테고리를 반환합니다.
     *
     * @param input 카테고리 이름
     * @return 입력 문자열에 해당하는 카테고리
     */
    public static Category fromString(String input) {
        if (input == null || input.isBlank()) {
            throw new CustomException(PostErrorCode.CATEGORY_REQUIRED);
        }

        for (Category category : values()) {
            if (category.name().equals(input)) {
                return category;
            }
        }

        throw new CustomException(PostErrorCode.INVALID_CATEGORY);
    }
}