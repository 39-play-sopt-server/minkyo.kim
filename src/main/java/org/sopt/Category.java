package org.sopt;

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

    public static Category fromNumber(int number) {
        for (Category category : values()) {
            if (category.getNumber() == number) {
                return category;
            }
        }

        throw new IllegalArgumentException("존재하지 않는 카테고리입니다.");
    }
}
