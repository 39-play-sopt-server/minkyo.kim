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

    public void updatePost(int index, String newTitle, String newContent) {
        Post post = posts.get(index);
        post.updateTitle(newTitle);
        post.updateContent(newContent);
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
