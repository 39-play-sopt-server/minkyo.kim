package org.sopt.post.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.PostSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.sopt.member.domain.QMember.member;
import static org.sopt.post.domain.QPost.post;

public class PostSearchRepositoryImpl implements PostSearchRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public PostSearchRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Post> search(
            PostSearchCondition condition,
            Pageable pageable
    ) {
        BooleanBuilder predicate = createPredicate(condition);

        List<Post> posts = jpaQueryFactory
                .selectFrom(post)
                .join(post.writer, member).fetchJoin()
                .where(predicate)
                .orderBy(
                        post.createdAt.desc(),
                        post.id.desc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = jpaQueryFactory
                .select(post.count())
                .from(post)
                .where(predicate)
                .fetchOne();

        return new PageImpl<>(
                posts,
                pageable,
                total != null ? total : 0
        );
    }

    private BooleanBuilder createPredicate(
            PostSearchCondition condition
    ) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (condition.title() != null && !condition.title().isBlank()) {
            predicate.and(post.title.contains(condition.title()));
        }

        if (condition.writerId() != null) {
            predicate.and(post.writer.id.eq(condition.writerId()));
        }

        if (condition.category() != null) {
            predicate.and(post.category.eq(condition.category()));
        }

        return predicate;
    }
}
