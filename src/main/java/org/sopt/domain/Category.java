package org.sopt.domain;

import org.sopt.exception.CustomException;
import org.sopt.exception.ErrorCode;

/**
 * 게시글이 속할 수 있는 카테고리와 사용자에게 표시할 이름을 정의합니다.
 */
public enum Category {
    NOTICE(1, "공지"),
    QUESTION(2, "질문"),
    GENERAL(3, "자유"),
    INFORMATION(4, "정보");

    private final int number;
    private final String name;

    Category(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public int getNumber() {
        return this.number;
    }

    public String getName() {
        return this.name;
    }

    /**
     * 입력 번호에 해당하는 카테고리를 반환합니다.
     *
     * @param number 카테고리 선택 번호
     * @return 선택 번호에 해당하는 카테고리
     */
    public static Category fromNumber(int number) {
        for (Category category : values()) {
            if (category.getNumber() == number) {
                return category;
            }
        }

        throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
    }
}