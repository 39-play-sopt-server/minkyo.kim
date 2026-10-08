package org.sopt.domain;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 게시글의 상태를 나타내는 도메인 객체입니다.
 * 수정 시 기존 객체의 상태를 바꾸지 않고 변경 내용이 반영된 새 객체를 반환합니다.
 */
public class Post {
    private Long id;
    private final String title;
    private final String content;
    private final Category category;
    private final String writer;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    /**
     * 저장 전 게시글을 생성합니다.
     *
     * @param title 게시글 제목
     * @param content 게시글 내용
     * @param category 게시글 카테고리
     * @param writer 게시글 작성자
     */
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

    /**
     * 저장소에서 생성한 ID를 게시글에 최초 한 번만 할당합니다.
     *
     * @param id 저장소에서 생성한 게시글 ID
     */
    public void assignId(long id) {
        if (this.id != null) {
            throw new IllegalStateException("게시글 ID는 다시 할당할 수 없습니다.");
        }
        this.id = id;
    }

    /**
     * 전달된 변경 사항을 반영한 새로운 게시글을 반환합니다.
     * 값이 없는 항목은 기존 값을 유지하며 작성자와 생성일은 변경하지 않습니다.
     *
     * @param newTitle 변경할 제목
     * @param newContent 변경할 내용
     * @param newCategory 변경할 카테고리
     * @return 변경 사항이 반영된 새로운 게시글
     */
    public Post with(
            Optional<String> newTitle,
            Optional<String> newContent,
            Optional<Category> newCategory
    ) {
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