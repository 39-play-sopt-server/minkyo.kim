package org.sopt;

import org.sopt.exception.CustomException;
import org.sopt.exception.ErrorCode;
import org.sopt.request.CreatePostRequest;
import org.sopt.request.UpdatePostRequest;

import java.util.List;

public class PostController {
    private final PostView view;
    private final PostService service;

    public PostController(PostView view, PostService service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        while (true) {
            try {
                view.printMenu();
                int command = view.readCommand();
                switch (command) {
                    case 1 -> createPost();
                    case 2 -> readPosts();
                    case 3 -> readPost();
                    case 4 -> updatePost();
                    case 5 -> deletePost();
                    case 6 -> {
                        view.printMessage("프로그램을 종료합니다.");
                        return;
                    }
                    default -> throw new CustomException(ErrorCode.INVALID_INPUT);
                }
            } catch (CustomException e) {
                view.printMessage(e.getMessage());
            }
        }
    }

    private void createPost() {
        String title = view.readTitle();
        String content = view.readContent();
        Category category = view.readCategory();
        String writer = view.readWriter();

        CreatePostRequest request = new CreatePostRequest(title, content, category, writer);

        service.createPost(request);
        view.printMessage("게시글이 작성되었습니다.");
    }

    private void readPosts() {
        List<Post> posts = service.readPosts();
        if (posts.isEmpty()) {
            view.printMessage("게시글이 없습니다.");
            return;
        }

        view.printPosts(posts);
    }

    private void readPost() {
        if (service.hasNoPosts()) {
            view.printMessage("게시글이 없습니다.");
            return;
        }

        long id = view.readPostNumber("조회할 게시글 번호: ");

        Post post = service.readPost(id);
        view.printPost(post);
    }

    private void updatePost() {
        if (service.hasNoPosts()) {
            view.printMessage("게시글이 없습니다.");
            return;
        }

        long id = view.readPostNumber("수정할 게시글 번호: ");

        UpdatePostRequest request = new UpdatePostRequest(
                view.readNewTitle(),
                view.readNewContent(),
                view.readNewCategory()
        );

        boolean isUpdated = service.updatePost(id, request);
        if (isUpdated) {
            view.printMessage("게시글이 수정되었습니다.");
        } else {
            view.printMessage("수정된 내용이 없습니다.");
        }
    }

    private void deletePost() {
        if (service.hasNoPosts()) {
            view.printMessage("게시글이 없습니다.");
            return;
        }

        long id = view.readPostNumber("삭제할 게시글 번호: ");

        service.deletePost(id);
        view.printMessage("게시글이 삭제되었습니다.");
    }
}