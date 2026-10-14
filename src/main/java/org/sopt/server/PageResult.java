package org.sopt.server;

import org.sopt.domain.Post;

import java.util.List;

public record PageResult(
        List<Post> posts,
        int totalPages,
        boolean hasNext
) {
}
