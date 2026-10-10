package org.sopt.request;

import org.sopt.domain.Category;

import java.util.Optional;

/**
 * 게시글 수정에 필요한 클라이언트 요청입니다.
 * 값이 없는 항목은 기존 게시글 값을 유지합니다.
 *
 * @param title 변경할 제목
 * @param content 변경할 내용
 * @param category 변경할 카테고리
 */
public record UpdatePostRequest(
        Optional<String> title,
        Optional<String> content,
        Optional<Category> category
) {
    /**
     * 수정할 값이 하나도 없는지 확인합니다.
     *
     * @return 모든 수정 항목이 비어 있으면 true, 아니면 false
     */
    public boolean isEmpty() {
        return title.isEmpty() && content().isEmpty() && category.isEmpty();
    }
}