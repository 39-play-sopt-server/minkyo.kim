package org.sopt.like.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import org.sopt.common.BaseEntity;

@Entity
public class PostLike extends BaseEntity {
    @EmbeddedId
    private Long id;
}
