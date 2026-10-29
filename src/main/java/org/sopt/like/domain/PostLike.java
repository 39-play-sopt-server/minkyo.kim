package org.sopt.like.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import org.sopt.common.BaseEntity;

/**
 * 게시글에 등록된 회원의 좋아요 상태를 나타내는 도메인 객체입니다.
 */
@Entity
public class PostLike extends BaseEntity {
    @EmbeddedId
    private PostLikeId id;

    protected PostLike() {}

    /**
     * 게시글과 회원을 식별하는 ID로 좋아요를 생성합니다.
     *
     * @param postId 좋아요를 등록할 게시글 ID
     * @param memberId 좋아요를 등록할 회원 ID
     */
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
