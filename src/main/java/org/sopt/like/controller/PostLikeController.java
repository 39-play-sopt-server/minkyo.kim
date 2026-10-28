package org.sopt.like.controller;

import org.sopt.common.response.CommonResponse;
import org.sopt.like.service.PostLikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts/{postId}/likes/{memberId}")
public class PostLikeController {
    private final PostLikeService postLikeService;

    public PostLikeController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

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
