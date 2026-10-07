package org.sopt;

import java.util.ArrayList;
import java.util.List;

public class PostRepository {
    private final List<Post> posts = new ArrayList<>();

    public void save(Post post) {
        posts.add(post);
    }

    public Post find(int index) {
        return posts.get(index);
    }

    public List<Post> findAll() {
        return List.copyOf(posts);
    }

    public void update(int index, Post updatedPost) {
        posts.set(index, updatedPost);
    }

    public void delete(int index) {
        posts.remove(index);
    }

    public boolean isEmpty() {
        return posts.isEmpty();
    }

    public boolean existsByIndex(int index) {
        return index >= 0 && index < posts.size();
    }
}
