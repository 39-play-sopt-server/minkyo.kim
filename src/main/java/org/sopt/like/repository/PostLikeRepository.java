package org.sopt.like.repository;

import org.sopt.like.domain.PostLike;
import org.sopt.like.domain.PostLikeId;
import org.sopt.like.repository.projection.PostLikeCountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {
    void deleteById_PostIdAndId_MemberId(Long postId, Long memberId);

    @Query("""
            SELECT COUNT(pl)
            FROM PostLike pl
            WHERE pl.id.postId = :postId
            """)
    long countByPostId(@Param("postId") Long postId);

    @Query("""
            SELECT pl.id.postId AS postId,
                   COUNT(pl) AS likeCount
            FROM PostLike pl
            WHERE pl.id.postId IN :postIds
            GROUP BY pl.id.postId
            """)
    List<PostLikeCountProjection> countByPostIds(@Param("postIds") List<Long> postIds);
}
