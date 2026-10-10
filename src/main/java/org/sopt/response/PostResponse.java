package org.sopt.response;

import org.sopt.domain.Post;

import java.time.LocalDateTime;

/**
 * 클라이언트에 전달할 게시글 정보를 나타냅니다.
 *
 * @param id 게시글 ID
 * @param title 게시글 제목
 * @param content 게시글 내용
 * @param category 카테고리 표시 이름
 * @param writer 게시글 작성자
 * @param createdAt 게시글 생성 시각
 * @param updatedAt 게시글 수정 시각
 */
public record PostResponse(
        long id,
        String title,
        String content,
        String category,
        String writer,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    /**
     * 서버의 게시글 도메인 객체를 클라이언트 응답으로 변환합니다.
     *
     * @param post 변환할 게시글
     * @return 클라이언트에 전달할 게시글 응답
     */
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCategory().getName(),
                post.getWriter(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}