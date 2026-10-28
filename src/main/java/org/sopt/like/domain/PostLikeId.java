package org.sopt.like.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PostLikeId implements Serializable {
    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private Long memberId;

    protected PostLikeId() {}

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
