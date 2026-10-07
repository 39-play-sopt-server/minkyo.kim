package org.sopt;

import org.sopt.request.CreatePostRequest;
import org.sopt.request.UpdatePostRequest;

import java.util.List;

public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public void createPost(CreatePostRequest request) {
        if (request.title().isBlank()) {
            System.out.println("제목은 필수 입력 항목입니다.");
            return;
        }
        if (request.content().isBlank()) {
            System.out.println("내용은 필수 입력 항목입니다.");
            return;
        }
        if (request.category() == null) {
            System.out.println("카테고리는 필수 입력 항목입니다.");
            return;
        }
        if (request.writer().isBlank()) {
            System.out.println("작성자는 필수 입력 항목입니다.");
            return;
        }

        Post post = new Post(request.title(), request.content(), request.category(), request.writer());
        repository.save(post);
    }

    public Post readPost(int index) {
        return repository.find(index);
    }

    public List<Post> readPosts() {
        return repository.findAll();
    }

    public boolean updatePost(int index, UpdatePostRequest request) {
        if (request.title().isEmpty() && request.content().isEmpty() && request.category().isEmpty()) {
            return false;
        }

        Post originalPost = repository.find(index);
        Post updatedPost = originalPost.with(
                request.title(),
                request.content(),
                request.category()
        );

        repository.update(index, updatedPost);

        return true;
    }

    public void deletePost(int index) {
        repository.delete(index);
    }

    public boolean hasNoPosts() {
        return repository.isEmpty();
    }

    public boolean isValidIndex(int index) {
        return repository.existsByIndex(index);
    }
}
