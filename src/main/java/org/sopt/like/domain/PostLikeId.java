package org.sopt.like.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 * 게시글 좋아요를 식별하는 게시글 ID와 회원 ID의 복합 키입니다.
 */
@Embeddable
public class PostLikeId implements Serializable {
    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private Long memberId;

    protected PostLikeId() {}

    /**
     * 게시글 ID와 회원 ID로 좋아요 식별자를 생성합니다.
     *
     * @param postId 게시글 ID
     * @param memberId 회원 ID
     */
    public PostLikeId(
            Long postId,
            Long memberId
    ) {
        this.postId = postId;
        this.memberId = memberId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof PostLikeId that)) {
            return false;
        }

        return Objects.equals(postId, that.postId)
                && Objects.equals(memberId, that.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, memberId);
    }
}
