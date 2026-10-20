package org.sopt.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.common.response.CommonResponse;
import org.sopt.post.dto.request.CreatePostRequest;
import org.sopt.post.dto.request.UpdatePostRequest;
import org.sopt.post.dto.response.PostPageResponse;
import org.sopt.post.dto.response.PostResponse;
import org.springframework.http.ResponseEntity;

/**
 * 게시글 API의 요청 조건과 응답 상황을 문서화합니다.
 */
@Tag(name = "게시글 API")
public interface PostApiDocs {
    @Operation(summary = "게시글 작성")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글 작성 성공"),
            @ApiResponse(
                    responseCode = "400",
                    description = """
                            - 제목･내용･작성자･카테고리 중 하나라도 비어 있는 경우
                            - 유효하지 않은 카테고리인 경우
                            """
            )
    })
    ResponseEntity<CommonResponse<Void>> createPost(
            CreatePostRequest request
    );

    @Operation(summary = "게시글 목록 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 목록 조회 성공"),
            @ApiResponse(
                    responseCode = "400",
                    description = """
                    - page 파라미터가 0 미만인 경우
                    - size 파라미터가 1 미만이거나 50을 초과한 경우
                    - 유효하지 않은 카테고리인 경우
                    """
            )
    })
    ResponseEntity<CommonResponse<PostPageResponse>> readPosts(
            @Parameter(description = """
                    카테고리별 조회 시 검색할 카테고리 \n
                    ** 검색 가능한 카테고리: `NOTICE`, `QUESTION`, `GENERAL`, `INFORMATION`
                    """)
            String category,
            @Parameter(description = "조회할 페이지 번호(0부터 시작)") int page,
            @Parameter(description = "한 페이지에서 조회할 게시글 수(1 이상 50 이하)") int size
    );

    @Operation(summary = "게시글 단건 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공"),
            @ApiResponse(responseCode = "404", description = "게시글이 존재하지 않는 경우")
    })
    ResponseEntity<CommonResponse<PostResponse>> readPost(
            @Parameter(description = "조회할 게시글 ID") long id
    );

    @Operation(summary = "게시글 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
            @ApiResponse(
                    responseCode = "400",
                    description = """
                            - 빈 값을 전달한 경우
                            - 유효하지 않은 카테고리인 경우
                            """
            ),
            @ApiResponse(responseCode = "404", description = "게시글이 존재하지 않는 경우")
    })
    ResponseEntity<CommonResponse<Void>> updatePost(
            @Parameter(description = "수정할 게시글 ID") long id,
            UpdatePostRequest request
    );

    @Operation(summary = "게시글 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 삭제 성공")
    })
    ResponseEntity<CommonResponse<Void>> deletePost(
            @Parameter(description = "삭제할 게시글 ID") long id
    );
}
