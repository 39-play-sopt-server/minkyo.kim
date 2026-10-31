package org.sopt.like.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.common.response.CommonResponse;
import org.springframework.http.ResponseEntity;

/**
 * 게시글 좋아요 API의 요청 조건과 응답 상황을 문서화합니다.
 */
@Tag(name = "게시글 좋아요 API")
public interface PostLikeApiDocs {
    @Operation(summary = "게시글 좋아요 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 좋아요 등록 성공"),
            @ApiResponse(responseCode = "404", description = "게시글 또는 회원이 존재하지 않는 경우")
    })
    ResponseEntity<CommonResponse<Void>> createPostLike(
            @Parameter(description = "좋아요를 등록할 게시글 ID") Long postId,
            @Parameter(description = "좋아요를 등록할 회원 ID") Long memberId
    );

    @Operation(summary = "게시글 좋아요 취소")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 좋아요 취소 성공"),
            @ApiResponse(responseCode = "404", description = "게시글 또는 회원이 존재하지 않는 경우")
    })
    ResponseEntity<CommonResponse<Void>> deletePostLike(
            @Parameter(description = "좋아요를 취소할 게시글 ID") Long postId,
            @Parameter(description = "좋아요를 취소할 회원 ID") Long memberId
    );
}
