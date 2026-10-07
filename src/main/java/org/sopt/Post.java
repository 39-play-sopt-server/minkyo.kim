package org.sopt;

public class Post {
    private String title;
    private String content;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public void update(String newTitle, String newContent) {
        this.title = newTitle;
        this.content = newContent;
    }
}