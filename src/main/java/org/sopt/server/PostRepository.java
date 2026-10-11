package org.sopt.server;

import org.sopt.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 게시글을 메모리에 저장하고 관리합니다.
 */
@Repository
public class PostRepository {
    private final Map<Long, Post> posts = new HashMap<>();

    private long nextId = 0L;

    /**
     * 새 게시글에 ID를 할당하고 저장합니다.
     *
     * @param post 저장할 게시글
     */
    public void save(Post post) {
        long id = nextId++;
        post.assignId(id);

        posts.put(id, post);
    }

    /**
     * 지정한 ID의 게시글을 반환합니다.
     *
     * @param id 조회할 게시글 ID
     * @return 조회된 게시글 또는 존재하지 않으면 null
     */
    public Post find(long id) {
        return posts.get(id);
    }

    /**
     * 저장된 전체 게시글을 반환합니다.
     *
     * @return 외부에서 구조를 변경할 수 없는 게시글 목록
     */
    public List<Post> findAll() {
        return List.copyOf(posts.values());
    }

    /**
     * 기존 게시글을 변경된 게시글로 교체합니다.
     *
     * @param updatedPost 변경 사항이 반영된 게시글
     */
    public void update(Post updatedPost) {
        posts.put(updatedPost.getId(), updatedPost);
    }

    /**
     * 지정한 ID의 게시글을 삭제합니다.
     *
     * @param id 삭제할 게시글 ID
     */
    public void delete(long id) {
        posts.remove(id);
    }

    /**
     * 지정한 ID의 게시글이 존재하는지 확인합니다.
     *
     * @param id 확인할 게시글 ID
     * @return 게시글이 존재하면 true, 아니면 false
     */
    public boolean existsById(long id) {
        return posts.containsKey(id);
    }
}