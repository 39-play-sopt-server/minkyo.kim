package org.sopt;

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
                default -> view.printMessage("잘못된 입력입니다.");
            }
        }
    }

    private void createPost() {
        String title = view.readTitle();
        String content = view.readContent();

        service.createPost(title, content);
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

        int index = view.readPostNumber("조회할 게시글 번호: ") - 1;
        // TODO: 인덱스 검증은 service 내에 예외 throw로 수정할 것
        if (!service.isValidIndex(index)) {
            view.printMessage("존재하지 않는 게시글입니다.");
            return;
        }

        Post post = service.readPost(index);
        view.printPost(post);
    }

    private void updatePost() {
        if (service.hasNoPosts()) {
            view.printMessage("게시글이 없습니다.");
            return;
        }

        int index = view.readPostNumber("수정할 게시글 번호: ") - 1;
        // TODO: 인덱스 검증은 service 내에 예외 throw로 수정할 것
        if (!service.isValidIndex(index)) {
            view.printMessage("존재하지 않는 게시글입니다.");
            return;
        }

        String newTitle = view.readTitle();
        String newContent = view.readContent();

        service.updatePost(index, newTitle, newContent);
        view.printMessage("게시글이 수정되었습니다.");
    }

    private void deletePost() {
        if (service.hasNoPosts()) {
            view.printMessage("게시글이 없습니다.");
            return;
        }

        int index = view.readPostNumber("삭제할 게시글 번호: ") - 1;
        // TODO: 인덱스 검증은 service 내에 예외 throw로 수정할 것
        if (!service.isValidIndex(index)) {
            view.printMessage("존재하지 않는 게시글입니다.");
            return;
        }

        service.deletePost(index);
        view.printMessage("게시글이 삭제되었습니다.");
    }
}