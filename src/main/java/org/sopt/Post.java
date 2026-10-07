package org.sopt;

import java.time.LocalDateTime;
import java.util.Optional;

public class Post {
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

        this.title = title;
        this.content = content;
        this.category = category;
        this.writer = writer;
        this.createdAt = now;
        this.updatedAt = now;
    }

    private Post(
            String title,
            String content,
            Category category,
            String writer,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.writer = writer;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public Post with(Optional<String> newTitle, Optional<String> newContent, Optional<Category> newCategory) {
        return new Post(
                newTitle.orElse(this.title),
                newContent.orElse(this.content),
                newCategory.orElse(this.category),
                this.writer,
                this.createdAt,
                LocalDateTime.now()
        );
    }
}