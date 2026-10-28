package org.sopt.like.repository;

import org.sopt.like.domain.PostLike;
import org.sopt.like.domain.PostLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {
    void deleteById_PostIdAndId_MemberId(Long postId, Long memberId);
}
