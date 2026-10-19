package org.sopt.server;

import jakarta.persistence.EntityManager;
import org.sopt.domain.Category;
import org.sopt.domain.Post;
import org.sopt.common.exception.CustomException;
import org.sopt.common.code.ErrorCode;
import org.sopt.request.CreatePostRequest;
import org.sopt.common.Pagination;
import org.sopt.request.UpdatePostRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 게시글 생성, 조회, 수정, 삭제와 관련된 비즈니스 로직을 처리합니다.
 */
@Service
public class PostService {
    private final PostRepository repository;
    private final EntityManager entityManager;

    public PostService(
            PostRepository repository,
            EntityManager entityManager
    ) {
        this.repository = repository;
        this.entityManager = entityManager;
    }

    /**
     * 생성 요청을 검증하고 새 게시글을 저장합니다.
     *
     * @param request 게시글 생성 요청
     */
    public void createPost(CreatePostRequest request) {
        Category category = Category.fromString(request.category());

        Post post = new Post(
                request.title(),
                request.content(),
                category,
                request.writer()
        );
        repository.save(post);
    }

    /**
     * 지정한 ID의 게시글 조회수를 증가시키고 게시글을 반환합니다.
     *
     * @param id 조회할 게시글 ID
     * @return 조회된 게시글
     */
    @Transactional
    public Post readPost(long id) {
        Post post = repository.findById(id)
                .orElseThrow(
                        () -> new CustomException(ErrorCode.POST_NOT_FOUND)
                );

        repository.increaseViewCount(id);
        entityManager.refresh(post);

        return post;
    }

    /**
     * 게시글 목록을 페이지 단위로 조회하며,
     * 카테고리가 전달되면 해당 카테고리를 가진 게시글만 반환합니다.
     *
     * @param categoryValue 조회할 카테고리, 없으면 전체 조회
     * @param pagination 페이지 조회 조건
     * @return 현재 페이지의 게시글과 페이지 상태
     */
    public PageResult readPosts(
            String categoryValue,
            Pagination pagination
    ) {
        Pageable pageRequest = PageRequest.of(
                pagination.page(),
                pagination.size(),
                Sort.by(Sort.Direction.DESC, "id")
        );

        Page<Post> result = categoryValue != null ?
                repository.findAllByCategory(
                        Category.fromString(categoryValue),
                        pageRequest
                )
                : repository.findAll(pageRequest);

        return new PageResult(
                result.getContent(),
                result.getTotalPages(),
                result.hasNext()
        );
    }

    /**
     * 지정한 ID의 게시글에 전달된 변경 사항을 적용합니다.
     *
     * @param id 수정할 게시글 ID
     * @param request 게시글 수정 요청
     */
    @Transactional
    public void updatePost(long id, UpdatePostRequest request) {
        Post post = repository.findById(id)
                .orElseThrow(
                        () -> new CustomException(ErrorCode.POST_NOT_FOUND)
                );

        Optional<Category> category = request
                .category()
                .map(Category::fromString);

        post.update(
                request.title(),
                request.content(),
                category
        );
    }

    /**
     * 지정한 ID의 게시글을 삭제합니다.
     *
     * @param id 삭제할 게시글 ID
     */
    public void deletePost(long id) {
        repository.deleteById(id);
    }
}