package org.sopt;

import org.sopt.exception.CustomException;
import org.sopt.request.CreatePostRequest;
import org.sopt.request.UpdatePostRequest;
import org.sopt.response.FailureResponse;
import org.sopt.response.PostResponse;
import org.sopt.response.Response;
import org.sopt.response.SuccessResponse;

import java.util.List;

public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

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