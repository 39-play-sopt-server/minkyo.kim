package org.sopt;

import java.util.List;

public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public void createPost(String title, String content) {
        Post post = new Post(title, content);
        repository.save(post);
    }

    public Post readPost(int index) {
        return repository.find(index);
    }

    public List<Post> readPosts() {
        return repository.findAll();
    }

    public boolean updatePost(int index, String newTitle, String newContent) {
        Post post = repository.find(index);

        if (newTitle.isBlank() && newContent.isBlank()) {
            return false;
        }

        String updatedTitle = newTitle.isBlank() ? post.getTitle() : newTitle;
        String updatedContent = newContent.isBlank() ? post.getContent() : newContent;

        repository.update(index, updatedTitle, updatedContent);

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
