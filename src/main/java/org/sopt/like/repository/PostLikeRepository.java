package org.sopt.like.repository;

import org.sopt.like.domain.PostLike;
import org.sopt.like.domain.PostLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {
    void deleteById_PostIdAndId_MemberId(Long postId, Long memberId);

    @Query("""
            SELECT COUNT(*)
            FROM PostLike pl
                WHERE pl.id.postId = :postId
            """)
    long countByPostId(@Param("postId") Long postId);
}
