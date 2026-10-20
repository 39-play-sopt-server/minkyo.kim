package org.sopt.post.dto.response;

import org.sopt.post.dto.PostPageResult;

import java.util.List;

/**
 * 클라이언트에 전달할 게시글 목록과 페이지 상태를 나타냅니다.
 *
 * @param posts 현재 페이지의 게시글 목록
 * @param totalPages 전체 페이지 수
 * @param hasNext 다음 페이지 존재 여부
 */
public record PostPageResponse(
        List<PostResponse> posts,
        int totalPages,
        boolean hasNext
) {
    /**
     * 내부 페이지 조회 결과를 클라이언트 응답으로 변환합니다.
     *
     * @param postPageResult 변환할 게시글 페이지 조회 결과
     * @return 클라이언트에 전달할 페이지 응답
     */
    public static PostPageResponse from(
            PostPageResult postPageResult
    ) {
        List<PostResponse> posts = postPageResult.posts().stream()
                .map(PostResponse::from)
                .toList();

        return new PostPageResponse(
                posts,
                postPageResult.totalPages(),
                postPageResult.hasNext()
        );
    }
}
