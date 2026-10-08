package org.sopt.response;

import org.sopt.Post;

import java.time.LocalDateTime;

public record PostResponse(
        long id,
        String title,
        String content,
        String category,
        String writer,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
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
