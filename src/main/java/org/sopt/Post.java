package org.sopt;

public class Post {
    private String title;
    private String content;
    private Category category;

    public Post(String title, String content, Category category) {
        this.title = title;
        this.content = content;
        this.category = category;
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

    public void update(String newTitle, String newContent, Category newCategory) {
        this.title = newTitle;
        this.content = newContent;
        this.category = newCategory;
    }
}