package org.sopt.post.repository;

import org.sopt.post.domain.Category;
import org.sopt.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = "writer")
    Page<Post> findAll(Pageable pageRequest);

    @EntityGraph(attributePaths = "writer")
    Page<Post> findAllByCategory(Category category, Pageable pageRequest);

    @Modifying
    @Query("""
            update Post p
            set p.viewCount = p.viewCount + 1
            where p.id = :id
            """)
    void increaseViewCount(@Param("id") long id);
}
