package org.sopt.post.dto;

import org.sopt.post.domain.Category;

public record PostSearchCondition(
        String title,
        Long writerId,
        Category category
) {
}
