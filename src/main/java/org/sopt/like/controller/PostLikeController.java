package org.sopt.like.controller;

import org.sopt.common.response.CommonResponse;
import org.sopt.like.service.PostLikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 클라이언트의 게시글 좋아요 요청을 Service에 전달하고 처리 결과를 공통 응답으로 변환합니다.
 */
@RestController
@RequestMapping("/api/v1/posts/{postId}/likes/{memberId}")
public class PostLikeController implements PostLikeApiDocs {
    private final PostLikeService postLikeService;

    public PostLikeController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

    /**
     * 지정한 게시글에 회원의 좋아요를 등록합니다.
     *
     * @param postId 좋아요를 등록할 게시글 ID
     * @param memberId 좋아요를 등록할 회원 ID
     * @return 좋아요 등록 처리 결과
     */
    @Override
    @PutMapping
    public ResponseEntity<CommonResponse<Void>> createPostLike(
            @PathVariable(required = true) Long postId,
            @PathVariable(required = true) Long memberId
    ) {
        postLikeService.createPostLike(postId, memberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(
                        "좋아요가 등록되었습니다.",
                        null
                ));
    }

    /**
     * 지정한 게시글에 등록된 회원의 좋아요를 취소합니다.
     *
     * @param postId 좋아요를 취소할 게시글 ID
     * @param memberId 좋아요를 취소할 회원 ID
     * @return 좋아요 취소 처리 결과
     */
    @Override
    @DeleteMapping
    public ResponseEntity<CommonResponse<Void>> deletePostLike(
            @PathVariable(required = true) Long postId,
            @PathVariable(required = true) Long memberId
    ) {
        postLikeService.deletePostLike(postId, memberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(
                        "좋아요가 취소되었습니다.",
                        null
                ));
    }
}
