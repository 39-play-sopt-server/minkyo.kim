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

    public void createPostLike(Long postId, Long memberId) {
        validatePostId(postId);
        validateMemberId(memberId);

        PostLike like = new PostLike(postId, memberId);
        if (postLikeRepository.existsById(like.getId())) {
            return;
        }

        postLikeRepository.save(like);
    }

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
