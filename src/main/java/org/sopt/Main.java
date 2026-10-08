package org.sopt;

import org.sopt.exception.CustomException;
import org.sopt.exception.ErrorCode;
import org.sopt.request.CreatePostRequest;
import org.sopt.request.UpdatePostRequest;
import org.sopt.response.FailureResponse;
import org.sopt.response.PostResponse;
import org.sopt.response.Response;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        PostView view = new PostView();
        PostRepository repository = new PostRepository();
        PostService service = new PostService(repository);
        PostController controller = new PostController(service);

        while (true) {
            view.printMenu();
            int command = view.readCommand();
            switch (command) {
                case 1 -> {
                    CreatePostRequest request = view.readCreatePostRequest();
                    Response<Void> response = controller.createPost(request);

                    view.printResponse(response);
                }
                case 2 -> {
                    Response<List<PostResponse>> response = controller.readPosts();

                    view.printPosts(response);
                }
                case 3 -> {
                    long id = view.readPostNumber("조회할 게시글 번호: ");
                    Response<PostResponse> response = controller.readPost(id);

                    view.printPost(response);
                }
                case 4 -> {
                    long id = view.readPostNumber("수정할 게시글 번호: ");
                    UpdatePostRequest request = view.readUpdatePostRequest();
                    Response<Void> response = controller.updatePost(id, request);

                    view.printResponse(response);
                }
                case 5 -> {
                    long id = view.readPostNumber("삭제할 게시글 번호: ");
                    Response<Void> response = controller.deletePost(id);

                    view.printResponse(response);
                }
                case 6 -> {
                    view.printMessage("프로그램을 종료합니다.");
                    return;
                }
                default -> {
                    Response<Void> response = new FailureResponse<>(
                            ErrorCode.INVALID_INPUT.getMessage()
                    );

                    view.printResponse(response);
                }
            }
        }
    }
}