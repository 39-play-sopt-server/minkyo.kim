package org.sopt.server;

import org.sopt.common.code.ErrorCode;
import org.sopt.common.exception.CustomException;
import org.sopt.domain.Category;
import org.sopt.domain.Post;
import org.sopt.common.Pagination;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.UnaryOperator;

/**
 * 게시글을 메모리에 저장하고 관리합니다.
 */
@Repository
public class PostRepository {
    private final Map<Long, Post> posts = new ConcurrentHashMap<>();

    private final AtomicLong nextId = new AtomicLong(0);

    /**
     * 새 게시글에 ID를 할당하고 저장합니다.
     *
     * @param post 저장할 게시글
     */
    public void save(Post post) {
        long id = nextId.getAndIncrement();
        post.assignId(id);

        posts.put(id, post);
    }

    /**
     * 지정한 ID의 게시글을 반환합니다.
     *
     * @param id 조회할 게시글 ID
     * @return 조회된 게시글 또는 존재하지 않으면 비어 있는 Optional
     */
    public Optional<Post> find(long id) {
        return Optional.ofNullable(posts.get(id));
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
     * 지정한 게시글의 최신 상태를 기준으로 변경을 원자적으로 적용합니다.
     *
     * @param id 변경할 게시글 ID
     * @param updater 현재 게시글을 변경된 게시글로 변환하는 함수
     */
    public void update(
            long id,
            UnaryOperator<Post> updater
    ) {
        posts.compute(id, (key, currentPost) -> {
            if (currentPost == null) {
                throw new CustomException(ErrorCode.POST_NOT_FOUND);
            }

            return updater.apply(currentPost);
        });
    }

    /**
     * 지정한 ID의 게시글을 삭제하고 삭제 결과를 반환합니다.
     *
     * @param id 삭제할 게시글 ID
     * @return 삭제된 게시글 또는 존재하지 않으면 비어 있는 Optional
     */
    public Optional<Post> delete(long id) {
        return Optional.ofNullable(posts.remove(id));
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