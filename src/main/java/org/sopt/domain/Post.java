package org.sopt.domain;

import java.time.LocalDateTime;
import java.util.Optional;

public class Post {
    private Long id;
    private final String title;
    private final String content;
    private final Category category;
    private final String writer;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Post(
            String title,
            String content,
            Category category,
            String writer
    ) {
        LocalDateTime now = LocalDateTime.now();

        this.id = null;
        this.title = title;
        this.content = content;
        this.category = category;
        this.writer = writer;
        this.createdAt = now;
        this.updatedAt = now;
    }

    private Post(
            long id,
            String title,
            String content,
            Category category,
            String writer,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.writer = writer;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public Category getCategory() {
        return this.category;
    }

    public String getWriter() {
        return this.writer;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void assignId(long id) {
        if (this.id != null) {
            throw new IllegalStateException("게시글 ID는 다시 할당할 수 없습니다.");
        }
        this.id = id;
    }

    public Post with(Optional<String> newTitle, Optional<String> newContent, Optional<Category> newCategory) {
        return new Post(
                this.id,
                newTitle.orElse(this.title),
                newContent.orElse(this.content),
                newCategory.orElse(this.category),
                this.writer,
                this.createdAt,
                LocalDateTime.now()
        );
    }
}