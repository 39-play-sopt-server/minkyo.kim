package org.sopt.like.controller;

import org.sopt.common.response.CommonResponse;
import org.sopt.like.dto.request.PostLikeCreateRequest;
import org.sopt.like.service.PostLikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
public class PostLikeController {
    private final PostLikeService postLikeService;

    public PostLikeController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

    @PostMapping("/{postId}/likes")
    public ResponseEntity<CommonResponse<Void>> createPostLike(
            @PathVariable(required = true) Long postId,
            @RequestBody(required = true) PostLikeCreateRequest request
    ) {
        postLikeService.createPostLike(postId, request.memberId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.success(
                        "좋아요가 등록되었습니다.",
                        null
                ));
    }
}
