package org.sopt.server;

import org.sopt.common.code.SuccessCode;
import org.sopt.domain.Post;
import org.sopt.request.CreatePostRequest;
import org.sopt.request.UpdatePostRequest;
import org.sopt.response.PostResponse;
import org.sopt.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 클라이언트 요청을 Service에 전달하고 처리 결과를 공통 응답으로 변환합니다.
 */
@RestController
@RequestMapping(path = "/api/v1/posts")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    /**
     * 게시글 생성 요청을 처리합니다.
     *
     * @param request 게시글 생성 요청
     * @return 게시글 생성 처리 결과
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createPost(
            @RequestBody(required = true) CreatePostRequest request
    ) {
        service.createPost(request);

        SuccessCode code = SuccessCode.POST_CREATED;

        return ResponseEntity
                .status(code.getHttpStatus())
                .body(ApiResponse.of(code, null));
    }

    /**
     * 전체 게시글 목록을 조회하며,
     * 카테고리가 전달되면 해당 카테고리를 가진 게시글만 반환합니다.
     *
     * @param category 조회할 카테고리, 생략하면 전체 조회
     * @return 게시글 목록 조회 결과
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponse>>> readPosts(
            @RequestParam(required = false) String category
    ) {
        List<PostResponse> posts = service.readPosts(category).stream()
                .map(PostResponse::from)
                .toList();

        SuccessCode code = posts.isEmpty()
                ? SuccessCode.NO_POSTS
                : SuccessCode.POSTS_READ;

        return ResponseEntity
                .status(code.getHttpStatus())
                .body(ApiResponse.of(code, posts));
    }

    /**
     * 지정한 ID의 게시글을 조회합니다.
     *
     * @param id 조회할 게시글 ID
     * @return 게시글 단건 조회 결과
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> readPost(
            @PathVariable long id
    ) {
        Post post = service.readPost(id);
        SuccessCode code = SuccessCode.POST_READ;

        return ResponseEntity
                .status(code.getHttpStatus())
                .body(ApiResponse.of(
                        code,
                        PostResponse.from(post)
                ));
    }

    /**
     * 지정한 ID의 게시글을 수정합니다.
     *
     * @param id 수정할 게시글 ID
     * @param request 게시글 수정 요청
     * @return 게시글 수정 처리 결과
     */
    @PatchMapping(path = "/{id}")
    public ResponseEntity<ApiResponse<Void>> updatePost(
            @PathVariable long id,
            @RequestBody(required = true) UpdatePostRequest request
    ) {
        boolean isUpdated = service.updatePost(id, request);
        SuccessCode code = isUpdated
                ? SuccessCode.POST_UPDATED
                : SuccessCode.POST_UNCHANGED;

        return ResponseEntity
                .status(code.getHttpStatus())
                .body(ApiResponse.of(code, null));
    }

    /**
     * 지정한 ID의 게시글을 삭제합니다.
     *
     * @param id 삭제할 게시글 ID
     * @return 게시글 삭제 처리 결과
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @PathVariable long id
    ) {
        service.deletePost(id);
        SuccessCode code = SuccessCode.POST_DELETED;

        return ResponseEntity
                .status(code.getHttpStatus())
                .body(ApiResponse.of(code, null));
    }
}