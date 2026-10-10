package org.sopt.server;

import org.sopt.domain.Post;
import org.sopt.exception.CustomException;
import org.sopt.request.CreatePostRequest;
import org.sopt.request.UpdatePostRequest;
import org.sopt.response.FailureResponse;
import org.sopt.response.PostResponse;
import org.sopt.response.Response;
import org.sopt.response.SuccessResponse;

import java.util.List;

/**
 * 클라이언트 요청을 Service에 전달하고 처리 결과를 공통 응답으로 변환합니다.
 */
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
    public Response<Void> createPost(CreatePostRequest request) {
        try {
            service.createPost(request);

            return new SuccessResponse<>(
                    "게시글이 작성되었습니다.",
                    null
            );
        } catch (CustomException e) {
            return new FailureResponse<>(
                    e.getMessage()
            );
        }
    }

    /**
     * 전체 게시글 목록을 조회합니다.
     *
     * @return 게시글 목록 조회 결과
     */
    public Response<List<PostResponse>> readPosts() {
        List<PostResponse> posts = service.readPosts().stream()
                .map(PostResponse::from)
                .toList();

        String message = posts.isEmpty() ? "게시글이 없습니다." : null;

        return new SuccessResponse<>(
                message,
                posts
        );
    }

    /**
     * 지정한 ID의 게시글을 조회합니다.
     *
     * @param id 조회할 게시글 ID
     * @return 게시글 단건 조회 결과
     */
    public Response<PostResponse> readPost(long id) {
        try {
            Post post = service.readPost(id);

            return new SuccessResponse<>(
                    null,
                    PostResponse.from(post)
            );
        } catch (CustomException e) {
            return new FailureResponse<>(
                    e.getMessage()
            );
        }
    }

    /**
     * 지정한 ID의 게시글을 수정합니다.
     *
     * @param id 수정할 게시글 ID
     * @param request 게시글 수정 요청
     * @return 게시글 수정 처리 결과
     */
    public Response<Void> updatePost(long id, UpdatePostRequest request) {
        try {
            boolean isUpdated = service.updatePost(id, request);
            String message = isUpdated ? "게시글이 수정되었습니다." : "수정된 내용이 없습니다.";

            return new SuccessResponse<>(
                    message,
                    null
            );
        } catch (CustomException e) {
            return new FailureResponse<>(
                    e.getMessage()
            );
        }
    }

    /**
     * 지정한 ID의 게시글을 삭제합니다.
     *
     * @param id 삭제할 게시글 ID
     * @return 게시글 삭제 처리 결과
     */
    public Response<Void> deletePost(long id) {
        try {
            service.deletePost(id);

            return new SuccessResponse<>(
                    "게시글이 삭제되었습니다.",
                    null
            );
        } catch (CustomException e) {
            return new FailureResponse<>(
                    e.getMessage()
            );
        }
    }
}