package org.sopt.server;

import org.sopt.domain.Post;
import org.sopt.exception.CustomException;
import org.sopt.request.CreatePostRequest;
import org.sopt.request.UpdatePostRequest;
import org.sopt.response.PostResponse;
import org.sopt.response.Response;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Response<Void>> createPost(
            @RequestBody(required = true) CreatePostRequest request
    ) {
        try {
            service.createPost(request);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Response<>(
                            "SUCCESS",
                            "게시글이 작성되었습니다.",
                            null
                    ));
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                    .body(new Response<>(
                            e.getErrorCode().name(),
                            e.getMessage(),
                            null
                    ));
        }
    }

    /**
     * 전체 게시글 목록을 조회합니다.
     *
     * @return 게시글 목록 조회 결과
     */
    @GetMapping
    public ResponseEntity<Response<List<PostResponse>>> readPosts() {
        List<PostResponse> posts = service.readPosts().stream()
                .map(PostResponse::from)
                .toList();

        String message = posts.isEmpty() ? "게시글이 없습니다." : null;

        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(
                        "SUCCESS",
                        message,
                        posts
                ));
    }

    /**
     * 지정한 ID의 게시글을 조회합니다.
     *
     * @param id 조회할 게시글 ID
     * @return 게시글 단건 조회 결과
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<Response<PostResponse>> readPost(
            @PathVariable long id
    ) {
        try {
            Post post = service.readPost(id);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Response<>(
                            "SUCCESS",
                            null,
                            PostResponse.from(post)
                    ));
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                    .body(new Response<>(
                            e.getErrorCode().name(),
                            e.getMessage(),
                            null
                    ));
        }
    }

    /**
     * 지정한 ID의 게시글을 수정합니다.
     *
     * @param id 수정할 게시글 ID
     * @param request 게시글 수정 요청
     * @return 게시글 수정 처리 결과
     */
    @PatchMapping(path = "/{id}")
    public ResponseEntity<Response<Void>> updatePost(
            @PathVariable long id,
            @RequestBody(required = true) UpdatePostRequest request
    ) {
        try {
            boolean isUpdated = service.updatePost(id, request);
            String message = isUpdated ? "게시글이 수정되었습니다." : "수정된 내용이 없습니다.";

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Response<>(
                            "SUCCESS",
                            message,
                            null
                    ));
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                    .body(new Response<>(
                            e.getErrorCode().name(),
                            e.getMessage(),
                            null
                    ));
        }
    }

    /**
     * 지정한 ID의 게시글을 삭제합니다.
     *
     * @param id 삭제할 게시글 ID
     * @return 게시글 삭제 처리 결과
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Response<Void>> deletePost(
            @PathVariable long id
    ) {
        try {
            service.deletePost(id);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Response<>(
                            "SUCCESS",
                            "게시글이 삭제되었습니다.",
                            null
                    ));
        } catch (CustomException e) {
            return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                    .body(new Response<>(
                            e.getErrorCode().name(),
                            e.getMessage(),
                            null
                    ));
        }
    }
}