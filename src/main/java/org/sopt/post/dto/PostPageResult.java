package org.sopt.post.dto;

import org.sopt.post.domain.Post;

import java.util.List;
import java.util.Map;

/**
 * 현재 페이지의 조회 결과와 페이지 상태를 나타냅니다.
 *
 * @param posts 현재 페이지에 포함된 게시글 목록
 * @param likeCounts 게시글 ID별 좋아요 수
 * @param totalPages 전체 페이지 수
 * @param hasNext 다음 페이지 존재 여부
 */
public record PostPageResult(
        List<Post> posts,
        Map<Long, Long> likeCounts,
        int totalPages,
        boolean hasNext
) {
}
