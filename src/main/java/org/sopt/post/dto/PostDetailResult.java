package org.sopt.post.dto;

import org.sopt.post.domain.Post;

public record PostDetailResult(
        Post post,
        long likeCount
) {
}
