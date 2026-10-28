package org.sopt.like.service;

import org.sopt.common.exception.CustomException;
import org.sopt.like.domain.PostLike;
import org.sopt.like.exception.PostLikeErrorCode;
import org.sopt.like.repository.PostLikeRepository;
import org.sopt.member.exception.MemberErrorCode;
import org.sopt.member.repository.MemberRepository;
import org.sopt.post.exception.PostErrorCode;
import org.sopt.post.repository.PostRepository;
import org.springframework.stereotype.Service;

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
        if (postId == null
                || !postRepository.existsById(postId)
        ) {
            throw new CustomException(PostErrorCode.POST_NOT_FOUND);
        }
        if (memberId == null
                || !memberRepository.existsByIdAndIsDeletedFalse(memberId)
        ) {
            throw new CustomException(MemberErrorCode.MEMBER_NOT_FOUND);
        }

        PostLike like = new PostLike(postId, memberId);
        if (postLikeRepository.existsById(like.getId())) {
            throw new CustomException(PostLikeErrorCode.LIKE_ALREADY_EXISTS);
        }

        postLikeRepository.save(like);
    }
}
