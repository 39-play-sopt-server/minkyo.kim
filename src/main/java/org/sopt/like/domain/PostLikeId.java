package org.sopt.like.domain;

import jakarta.persistence.Column;

import java.io.Serializable;

public class PostLikeId implements Serializable {
    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private Long userId;
}
