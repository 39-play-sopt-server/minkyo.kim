package org.sopt.server;

import org.sopt.domain.Post;
import org.sopt.exception.CustomException;
import org.sopt.exception.ErrorCode;
import org.sopt.request.CreatePostRequest;
import org.sopt.request.UpdatePostRequest;

import java.util.List;

public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public void createPost(CreatePostRequest request) {
        validateRequest(request);

        Post post = new Post(request.title(), request.content(), request.category(), request.writer());
        repository.save(post);
    }

    public Post readPost(long id) {
        return getPost(id);
    }

    public List<Post> readPosts() {
        return repository.findAll();
    }

    public boolean updatePost(long id, UpdatePostRequest request) {
        Post originalPost = getPost(id);

        if (request.title().isEmpty() && request.content().isEmpty() && request.category().isEmpty()) {
            return false;
        }

        Post updatedPost = originalPost.with(
                request.title(),
                request.content(),
                request.category()
        );

        repository.update(updatedPost);

        return true;
    }

    public void deletePost(long id) {
        getPost(id);
        repository.delete(id);
    }

    private Post getPost(long id) {
        if (!repository.existsById(id)) {
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        }

        return repository.find(id);
    }

    private void validateRequest(CreatePostRequest request) {
        if (request.title().isBlank()) {
            throw new CustomException(ErrorCode.TITLE_REQUIRED);
        }
        if (request.content().isBlank()) {
            throw new CustomException(ErrorCode.CONTENT_REQUIRED);
        }
        if (request.category() == null) {
            throw new CustomException(ErrorCode.CATEGORY_REQUIRED);
        }
        if (request.writer().isBlank()) {
            throw new CustomException(ErrorCode.WRITER_REQUIRED);
        }
    }
}
