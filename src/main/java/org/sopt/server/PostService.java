package org.sopt.server;

import org.sopt.domain.Category;
import org.sopt.domain.Post;
import org.sopt.common.exception.CustomException;
import org.sopt.common.code.ErrorCode;
import org.sopt.request.CreatePostRequest;
import org.sopt.common.Pagination;
import org.sopt.request.UpdatePostRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 게시글 생성, 조회, 수정, 삭제와 관련된 비즈니스 로직을 처리합니다.
 */
@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
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
     * 지정한 ID의 게시글을 조회합니다.
     *
     * @param id 조회할 게시글 ID
     * @return 조회된 게시글
     */
    public Post readPost(long id) {
        Post post = getPost(id);
        post.increaseViewCount();

        return post;
    }

    /**
     * 전체 게시글 목록을 조회하며,
     * 카테고리가 전달되면 해당 카테고리를 가진 게시글만 반환합니다.
     *
     * @param category 조회할 카테고리, 없으면 전체 조회
     * @return 조회된 게시글 목록
     */
    public List<Post> readPosts(
            String category,
            Pagination pagination
    ) {
        if (category != null) {
            return readPostsByCategory(category, pagination);
        }

        return repository.findAll(pagination);
    }

    /**
     * 지정한 ID의 게시글에 전달된 변경 사항을 적용합니다.
     *
     * @param id 수정할 게시글 ID
     * @param request 게시글 수정 요청
     * @return 수정할 내용이 있으면 true, 없으면 false
     */
    public boolean updatePost(long id, UpdatePostRequest request) {
        Post originalPost = getPost(id);

        if (request.isEmpty()) {
            return false;
        }

        Optional<Category> category = request
                .category()
                .map(Category::fromString);

        Post updatedPost = originalPost.with(
                request.title(),
                request.content(),
                category
        );
        repository.update(updatedPost);

        return true;
    }

    /**
     * 지정한 ID의 게시글을 삭제합니다.
     *
     * @param id 삭제할 게시글 ID
     */
    public void deletePost(long id) {
        getPost(id);
        repository.delete(id);
    }

    /**
     * 게시글 존재 여부를 확인한 뒤 게시글을 반환합니다.
     *
     * @param id 조회할 게시글 ID
     * @return 조회된 게시글
     */
    private Post getPost(long id) {
        if (!repository.existsById(id)) {
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        }

        return repository.find(id);
    }

    private List<Post> readPostsByCategory(
            String categoryName,
            Pagination pagination
    ) {
        Category category = Category.fromString(categoryName);

        return repository.findAllByCategory(category, pagination);
    }
}