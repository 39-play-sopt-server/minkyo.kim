package org.sopt.post.controller;

import org.sopt.post.dto.PostDetailResult;
import org.sopt.post.dto.PostPageResult;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.request.CreatePostRequest;
import org.sopt.common.Pagination;
import org.sopt.post.dto.request.UpdatePostRequest;
import org.sopt.post.dto.response.PostPageResponse;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.common.response.CommonResponse;
import org.sopt.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 클라이언트 요청을 Service에 전달하고 처리 결과를 공통 응답으로 변환합니다.
 */
@RestController
@RequestMapping(path = "/api/v1/posts")
public class PostController implements PostApiDocs {
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
    @Override
    @PostMapping
    public ResponseEntity<CommonResponse<Void>> createPost(
            @RequestBody(required = true) CreatePostRequest request
    ) {
        service.createPost(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.success(
                        "게시글이 작성되었습니다.",
                        null
                ));
    }

    /**
     * 게시글 목록을 페이지 단위로 조회하며,
     * 카테고리가 전달되면 해당 카테고리를 가진 게시글만 반환합니다.
     *
     * @param title 검색할 제목, 생략하면 제목 조건 미적용
     * @param writerId 작성자 회원 ID, 생략하면 작성자 조건 미적용
     * @param category 조회할 카테고리, 생략하면 카테고리 조건 미적용
     * @param page 조회할 페이지 번호
     * @param size 한 페이지에서 조회할 게시글 수
     * @return 게시글 목록 조회 결과
     */
    @Override
    @GetMapping
    public ResponseEntity<CommonResponse<PostPageResponse>> readPosts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long writerId,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pagination pagination = new Pagination(page, size);
        PostPageResult postPageResult = service.readPosts(
                title,
                writerId,
                category,
                pagination
        );

        PostPageResponse postPageResponse = PostPageResponse.from(postPageResult);

        String message = postPageResult.totalPages() == 0
                ? "게시글이 없습니다."
                : null;

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(
                        message,
                        postPageResponse
                ));
    }

    /**
     * 지정한 ID의 게시글을 조회합니다.
     *
     * @param id 조회할 게시글 ID
     * @return 게시글 단건 조회 결과
     */
    @Override
    @GetMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<PostResponse>> readPost(
            @PathVariable Long id
    ) {
        PostDetailResult postDetailResult = service.readPost(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(
                        null,
                        PostResponse.from(
                                postDetailResult.post(),
                                postDetailResult.likeCount()
                        )
                ));
    }

    /**
     * 지정한 ID의 게시글을 수정합니다.
     *
     * @param id 수정할 게시글 ID
     * @param request 게시글 수정 요청
     * @return 게시글 수정 처리 결과
     */
    @Override
    @PatchMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<Void>> updatePost(
            @PathVariable Long id,
            @RequestBody(required = true) UpdatePostRequest request
    ) {
        service.updatePost(id, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(
                        "게시글이 수정되었습니다.",
                        null
                ));
    }

    /**
     * 지정한 ID의 게시글을 삭제합니다.
     *
     * @param id 삭제할 게시글 ID
     * @return 게시글 삭제 처리 결과
     */
    @Override
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<Void>> deletePost(
            @PathVariable Long id
    ) {
        service.deletePost(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.success(
                        "게시글이 삭제되었습니다.",
                        null
                ));
    }
}