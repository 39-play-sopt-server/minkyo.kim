package org.sopt.server;

import org.sopt.domain.Post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostRepository {
    private final Map<Long, Post> posts = new HashMap<>();

    private long nextId = 0L;

    public void save(Post post) {
        long id = nextId++;
        post.assignId(id);

        posts.put(id, post);
    }

    public Post find(long id) {
        return posts.get(id);
    }

    public List<Post> findAll() {
        return List.copyOf(posts.values());
    }

    public void update(Post updatedPost) {
        posts.put(updatedPost.getId(), updatedPost);
    }

    public void delete(long id) {
        posts.remove(id);
    }

    public boolean isEmpty() {
        return posts.isEmpty();
    }

    public boolean existsById(long id) {
        return posts.containsKey(id);
    }
}
