package org.sopt.domain;

import jakarta.persistence.*;
import org.sopt.common.code.ErrorCode;
import org.sopt.common.exception.CustomException;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 게시글의 상태를 나타내는 도메인 객체입니다.
 */
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private long viewCount;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    protected Post() {}
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
        validate(title, content, category, writer);

        LocalDateTime now = LocalDateTime.now();

        this.id = null;
        this.title = title;
        this.content = content;
        this.category = category;
        this.writer = writer;
        this.viewCount = 0;
        this.createdAt = now;
        this.updatedAt = now;
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

    public long getViewCount() {
        return this.viewCount;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    /**
     * 게시글 객체를 수정합니다.
     * 값이 없는 항목은 기존 값을 유지하며 작성자･생성일･조회수는 변경하지 않습니다.
     *
     * @param newTitle 변경할 제목
     * @param newContent 변경할 내용
     * @param newCategory 변경할 카테고리
     */
    public void update(
            Optional<String> newTitle,
            Optional<String> newContent,
            Optional<Category> newCategory
    ) {
        validate(
                newTitle.orElse(this.title),
                newContent.orElse(this.content),
                newCategory.orElse(this.category),
                this.writer
        );

        this.title = newTitle.orElse(this.title);
        this.content = newContent.orElse(this.content);
        this.category = newCategory.orElse(this.category);

        this.updatedAt = LocalDateTime.now();
    }

    private static void validate(
            String title,
            String content,
            Category category,
            String writer
    ) {
        if (title == null || title.isBlank()) {
            throw new CustomException(ErrorCode.TITLE_REQUIRED);
        }
        if (content == null || content.isBlank()) {
            throw new CustomException(ErrorCode.CONTENT_REQUIRED);
        }
        if (category == null) {
            throw new CustomException(ErrorCode.CATEGORY_REQUIRED);
        }
        if (writer == null || writer.isBlank()) {
            throw new CustomException(ErrorCode.WRITER_REQUIRED);
        }
    }
}