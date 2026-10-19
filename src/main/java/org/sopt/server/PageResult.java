package org.sopt.server;

import org.sopt.domain.Post;

import java.util.List;

/**
 * 현재 페이지의 조회 결과와 페이지 상태를 나타냅니다.
 *
 * @param posts 현재 페이지에 포함된 게시글 목록
 * @param totalPages 전체 페이지 수
 * @param hasNext 다음 페이지 존재 여부
 */
public record PageResult(
        List<Post> posts,
        int totalPages,
        boolean hasNext
) {
}
