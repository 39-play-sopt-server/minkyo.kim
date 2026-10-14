package org.sopt.response;

import org.sopt.server.PageResult;

import java.util.List;

public record PageResponse(
        List<PostResponse> posts,
        int totalPages,
        boolean hasNext
) {
    public static PageResponse from(
            PageResult pageResult
    ) {
        List<PostResponse> posts = pageResult.posts().stream()
                .map(PostResponse::from)
                .toList();

        return new PageResponse(
                posts,
                pageResult.totalPages(),
                pageResult.hasNext()
        );
    }
}
