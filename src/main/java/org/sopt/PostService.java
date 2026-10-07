package org.sopt;

import java.util.List;
import java.util.Optional;

public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public void createPost(String title, String content, Category category) {
        if (title.isBlank()) {
            System.out.println("제목은 필수 입력 항목입니다.");
            return;
        }
        if (content.isBlank()) {
            System.out.println("내용은 필수 입력 항목입니다.");
            return;
        }
        if (category == null) {
            System.out.println("카테고리는 필수 입력 항목입니다.");
            return;
        }

        Post post = new Post(title, content, category);
        repository.save(post);
    }

    public Post readPost(int index) {
        return repository.find(index);
    }

    public List<Post> readPosts() {
        return repository.findAll();
    }

    public boolean updatePost(int index, String newTitle, String newContent, Optional<Category> newCategory) {
        Post post = repository.find(index);

        if (newTitle.isBlank() && newContent.isBlank() && newCategory.isEmpty()) {
            return false;
        }

        String updatedTitle = newTitle.isBlank() ? post.getTitle() : newTitle;
        String updatedContent = newContent.isBlank() ? post.getContent() : newContent;
        Category updatedCategory = newCategory.orElse(post.getCategory());

        repository.update(index, updatedTitle, updatedContent, updatedCategory);

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
