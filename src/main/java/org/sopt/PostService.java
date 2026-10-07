package org.sopt;

import java.util.ArrayList;
import java.util.List;

public class PostService {
    private final PostRepository repository;
    private final List<Post> posts = new ArrayList<>();

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public void createPost(String title, String content) {
        posts.add(new Post(title, content));
    }

    public Post readPost(int index) {
        return posts.get(index);
    }

    public List<Post> readPosts() {
        return List.copyOf(posts);
    }

    public boolean updatePost(int index, String newTitle, String newContent) {
        Post post = posts.get(index);
        boolean isUpdated = false;

        if (!newTitle.isBlank()) {
            post.updateTitle(newTitle);
            isUpdated = true;
        }
        if (!newContent.isBlank()) {
            post.updateContent(newContent);
            isUpdated = true;
        }

        return isUpdated;
    }

    public void deletePost(int index) {
        posts.remove(index);
    }

    public boolean hasNoPosts() {
        return posts.isEmpty();
    }

    public boolean isValidIndex(int index) {
        return index >= 0 && index < posts.size();
    }
}
