package org.sopt.request;

import org.sopt.domain.Category;

/**
 * 게시글 생성에 필요한 클라이언트 요청입니다.
 *
 * @param title 게시글 제목
 * @param content 게시글 내용
 * @param category 게시글 카테고리
 * @param writer 게시글 작성자
 */
public record CreatePostRequest(
        String title,
        String content,
        Category category,
        String writer
) {
}