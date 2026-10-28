package org.sopt.like.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import org.sopt.common.BaseEntity;

@Entity
public class PostLike extends BaseEntity {
    @EmbeddedId
    private PostLikeId id;

    protected PostLike() {}

    public PostLike(Long postId, Long memberId) {
        this.id = new PostLikeId(
                postId,
                memberId
        );
    }

    public PostLikeId getId() {
        return id;
    }
}
