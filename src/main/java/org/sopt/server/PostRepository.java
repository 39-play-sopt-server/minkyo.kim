package org.sopt.server;

import org.sopt.domain.Category;
import org.sopt.domain.Post;
import org.sopt.common.Pagination;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
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
     * 저장된 게시글을 최신순으로 정렬하여 지정한 페이지에 포함된 게시글 목록을 반환합니다.
     *
     * @param pagination 페이지 조회 조건
     * @return 현재 페이지의 게시글과 페이지 상태
     */
    public PageResult findAll(Pagination pagination) {
        List<Post> sortedPosts = posts.values().stream()
                .sorted(Comparator.comparingLong(Post::getId).reversed())
                .toList();

        return createPageResult(sortedPosts, pagination);
    }

    /**
     * 지정한 카테고리의 게시글을 최신순으로 정렬하여 지정한 페이지에 포함된 게시글 목록을 반환합니다.
     *
     * @param category 조회할 게시글 카테고리
     * @param pagination 페이지 조회 조건
     * @return 현재 페이지의 게시글과 페이지 상태
     */
    public PageResult findAllByCategory(
            Category category,
            Pagination pagination
    ) {
        List<Post> filteredPosts = posts.values().stream()
                .filter(post -> post.getCategory() == category)
                .sorted(Comparator.comparingLong(Post::getId).reversed())
                .toList();

        return createPageResult(filteredPosts, pagination);
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

    private PageResult createPageResult(
            List<Post> postsToPaginate,
            Pagination pagination
    ) {
        List<Post> pagedPosts = postsToPaginate.stream()
                .skip(pagination.offset())
                .limit(pagination.size())
                .toList();
        int totalPages = (postsToPaginate.size() + pagination.size() - 1) / pagination.size();
        boolean hasNext = pagination.page() + 1 < totalPages;

        return new PageResult(pagedPosts, totalPages, hasNext);
    }
}