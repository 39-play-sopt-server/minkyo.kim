package org.sopt;

import org.sopt.client.PostView;
import org.sopt.request.CreatePostRequest;
import org.sopt.request.UpdatePostRequest;
import org.sopt.response.PostResponse;
import org.sopt.response.Response;
import org.sopt.server.PostController;
import org.sopt.server.PostRepository;
import org.sopt.server.PostService;

import java.util.List;

/**
 * 게시판 애플리케이션을 실행하고 클라이언트의 전체 요청 흐름을 조정합니다.
 */
public class Main {
    /**
     * 클라이언트와 서버 객체를 조립하고 사용자 명령을 반복해서 처리합니다.
     *
     * @param args 프로그램 실행 인자
     */
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

                    Response<PostResponse> findResponse = controller.readPost(id);
                    if (!findResponse.success()) {
                        view.printResponse(findResponse);
                    } else {
                        UpdatePostRequest request = view.readUpdatePostRequest();
                        Response<Void> response = controller.updatePost(id, request);

                        view.printResponse(response);
                    }
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
                    view.printMessage("잘못된 입력입니다.");
                }
            }
        }
    }
}