package org.sopt.like.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.common.response.CommonResponse;
import org.springframework.http.ResponseEntity;

/**
 * 게시글 좋아요 API의 요청 조건과 응답 상황을 문서화합니다.
 */
@Tag(name = "게시글 좋아요 API")
@SecurityRequirement(name = "bearerAuth")
public interface PostLikeApiDocs {
    @Operation(summary = "게시글 좋아요 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 좋아요 등록 성공"),
            @ApiResponse(responseCode = "401", description = "인증 토큰이 없거나 유효하지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "게시글 또는 회원이 존재하지 않는 경우")
    })
    ResponseEntity<CommonResponse<Void>> createPostLike(
            @Parameter(hidden = true) Long memberId,
            @Parameter(description = "좋아요를 등록할 게시글 ID") Long postId
    );

    @Operation(summary = "게시글 좋아요 취소")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 좋아요 취소 성공"),
            @ApiResponse(responseCode = "401", description = "인증 토큰이 없거나 유효하지 않은 경우"),
            @ApiResponse(responseCode = "404", description = "게시글 또는 회원이 존재하지 않는 경우")
    })
    ResponseEntity<CommonResponse<Void>> deletePostLike(
            @Parameter(hidden = true) Long memberId,
            @Parameter(description = "좋아요를 취소할 게시글 ID") Long postId
    );
}
