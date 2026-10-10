package org.sopt.server;

import org.sopt.domain.Post;
import org.sopt.exception.CustomException;
import org.sopt.exception.ErrorCode;
import org.sopt.request.CreatePostRequest;
import org.sopt.request.UpdatePostRequest;
import org.springframework.stereotype.Service;

import java.util.List;

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
        validateRequest(request);

        Post post = new Post(
                request.title(),
                request.content(),
                request.category(),
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
        return getPost(id);
    }

    /**
     * 저장된 전체 게시글을 조회합니다.
     *
     * @return 전체 게시글 목록
     */
    public List<Post> readPosts() {
        return repository.findAll();
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

        Post updatedPost = originalPost.with(
                request.title(),
                request.content(),
                request.category()
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

    /**
     * 게시글 생성 요청의 필수 입력값을 검증합니다.
     *
     * @param request 검증할 게시글 생성 요청
     */
    private void validateRequest(CreatePostRequest request) {
        if (request.title().isBlank()) {
            throw new CustomException(ErrorCode.TITLE_REQUIRED);
        }
        if (request.content().isBlank()) {
            throw new CustomException(ErrorCode.CONTENT_REQUIRED);
        }
        if (request.category() == null) {
            throw new CustomException(ErrorCode.CATEGORY_REQUIRED);
        }
        if (request.writer().isBlank()) {
            throw new CustomException(ErrorCode.WRITER_REQUIRED);
        }
    }
}