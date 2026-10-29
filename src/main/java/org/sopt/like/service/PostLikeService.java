package org.sopt.like.service;

import org.sopt.common.exception.CustomException;
import org.sopt.like.domain.PostLike;
import org.sopt.like.repository.PostLikeRepository;
import org.sopt.member.exception.MemberErrorCode;
import org.sopt.member.repository.MemberRepository;
import org.sopt.post.exception.PostErrorCode;
import org.sopt.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시글 좋아요 등록과 취소에 관련된 비즈니스 로직을 처리합니다.
 */
@Service
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostLikeService(
            PostLikeRepository postLikeRepository,
            PostRepository postRepository,
            MemberRepository memberRepository
    ) {
        this.postLikeRepository = postLikeRepository;
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * 게시글과 회원의 존재 여부를 확인하고 좋아요를 등록합니다.
     * 이미 등록된 좋아요는 다시 저장하지 않습니다.
     *
     * @param postId 좋아요를 등록할 게시글 ID
     * @param memberId 좋아요를 등록할 회원 ID
     */
    public void createPostLike(Long postId, Long memberId) {
        validatePostId(postId);
        validateMemberId(memberId);

        PostLike like = new PostLike(postId, memberId);
        if (postLikeRepository.existsById(like.getId())) {
            return;
        }

        postLikeRepository.save(like);
    }

    /**
     * 게시글과 회원의 존재 여부를 확인하고 좋아요를 취소합니다.
     *
     * @param postId 좋아요를 취소할 게시글 ID
     * @param memberId 좋아요를 취소할 회원 ID
     */
    @Transactional
    public void deletePostLike(Long postId, Long memberId) {
        validatePostId(postId);
        validateMemberId(memberId);

        postLikeRepository.deleteById_PostIdAndId_MemberId(
                postId,
                memberId
        );
    }

    private void validatePostId(Long postId) {
        if (postId == null
                || !postRepository.existsById(postId)
        ) {
            throw new CustomException(PostErrorCode.POST_NOT_FOUND);
        }
    }

    private void validateMemberId(Long memberId) {
        if (memberId == null
                || !memberRepository.existsByIdAndIsDeletedFalse(memberId)
        ) {
            throw new CustomException(MemberErrorCode.MEMBER_NOT_FOUND);
        }
    }
}
