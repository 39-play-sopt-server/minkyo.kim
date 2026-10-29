package org.sopt.post.service;

import jakarta.persistence.EntityManager;
import org.sopt.like.repository.PostLikeRepository;
import org.sopt.like.repository.projection.PostLikeCountProjection;
import org.sopt.member.domain.Member;
import org.sopt.member.exception.MemberErrorCode;
import org.sopt.member.repository.MemberRepository;
import org.sopt.post.domain.Category;
import org.sopt.post.domain.Post;
import org.sopt.common.exception.CustomException;
import org.sopt.post.dto.PostDetailResult;
import org.sopt.post.dto.PostSearchCondition;
import org.sopt.post.exception.PostErrorCode;
import org.sopt.post.dto.request.CreatePostRequest;
import org.sopt.common.Pagination;
import org.sopt.post.dto.request.UpdatePostRequest;
import org.sopt.post.dto.PostPageResult;
import org.sopt.post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 게시글 생성, 조회, 수정, 삭제와 관련된 비즈니스 로직을 처리합니다.
 */
@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostLikeRepository postLikeRepository;
    private final EntityManager entityManager;

    public PostService(
            PostRepository postRepository,
            MemberRepository memberRepository,
            PostLikeRepository postLikeRepository,
            EntityManager entityManager
    ) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.postLikeRepository = postLikeRepository;
        this.entityManager = entityManager;
    }

    /**
     * 생성 요청을 검증하고 새 게시글을 저장합니다.
     *
     * @param request 게시글 생성 요청
     */
    public void createPost(CreatePostRequest request) {
        if (request.writerId() == null) {
            throw new CustomException(PostErrorCode.WRITER_REQUIRED);
        }

        Member writer = memberRepository.findByIdAndIsDeletedFalse(request.writerId())
                .orElseThrow(
                        () -> new CustomException(MemberErrorCode.MEMBER_NOT_FOUND)
                );
        Category category = Category.fromString(request.category());

        Post post = new Post(
                request.title(),
                request.content(),
                category,
                writer
        );
        postRepository.save(post);
    }

    /**
     * 지정한 ID의 게시글 조회수를 증가시키고 게시글과 좋아요 수를 반환합니다.
     *
     * @param id 조회할 게시글 ID
     * @return 조회된 게시글과 좋아요 수
     */
    @Transactional
    public PostDetailResult readPost(Long id) {
        Post post = getPost(id);

        postRepository.increaseViewCount(id);
        entityManager.refresh(post);

        long likeCount = postLikeRepository.countByPostId(id);

        return new PostDetailResult(post, likeCount);
    }

    /**
     * 게시글 목록을 페이지 단위로 조회하며,
     * 제목･작성자･카테고리가 전달되면 해당 조건에 맞는 게시글만 반환합니다.
     *
     * @param title 검색할 제목, 없으면 제목 조건 미적용
     * @param writerId 작성자 ID, 없으면 작성자 조건 미적용
     * @param categoryValue 조회할 카테고리, 없으면 카테고리 조건 미적용
     * @param pagination 페이지 조회 조건
     * @return 현재 페이지의 게시글과 페이지 상태
     */
    public PostPageResult readPosts(
            String title,
            Long writerId,
            String categoryValue,
            Pagination pagination
    ) {
        PostSearchCondition condition = createSearchCondition(
                title,
                writerId,
                categoryValue
        );
        Pageable pageRequest = PageRequest.of(
                pagination.page(),
                pagination.size()
        );

        Page<Post> result = postRepository.search(condition, pageRequest);
        Map<Long, Long> likeCounts = getLikeCount(result.getContent());

        return new PostPageResult(
                result.getContent(),
                likeCounts,
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
    public void updatePost(Long id, UpdatePostRequest request) {
        Post post = getPost(id);

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
    public void deletePost(Long id) {
        Post post = getPost(id);
        postRepository.delete(post);
    }

    private Post getPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(
                        () -> new CustomException(PostErrorCode.POST_NOT_FOUND)
                );
    }

    private PostSearchCondition createSearchCondition(
            String title,
            Long writerId,
            String categoryValue
    ) {
        Category category = categoryValue == null
                ? null
                : Category.fromString(categoryValue);

        return new PostSearchCondition(
                title,
                writerId,
                category
        );
    }

    private Map<Long, Long> getLikeCount(List<Post> posts) {
        if (posts.isEmpty()) {
            return Map.of();
        }

        List<Long> postIds = posts.stream()
                .map(Post::getId)
                .toList();

        return postLikeRepository
                .countByPostIds(postIds)
                .stream()
                .collect(Collectors.toMap(
                            PostLikeCountProjection::getPostId,
                            PostLikeCountProjection::getLikeCount
                ));
    }
}