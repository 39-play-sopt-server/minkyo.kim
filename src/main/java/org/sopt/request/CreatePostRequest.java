package org.sopt.request;

import org.sopt.domain.Category;

public record CreatePostRequest(
        String title,
        String content,
        Category category,
        String writer
) {
}
