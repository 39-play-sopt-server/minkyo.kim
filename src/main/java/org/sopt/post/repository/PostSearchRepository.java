package org.sopt.post.repository;

import org.sopt.post.domain.Post;
import org.sopt.post.dto.PostSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostSearchRepository {
    Page<Post> search(
            PostSearchCondition condition,
            Pageable pageable
    );
}
