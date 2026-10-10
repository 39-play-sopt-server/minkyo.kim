package org.sopt.client;

import org.sopt.domain.Category;
import org.sopt.exception.CustomException;
import org.sopt.request.CreatePostRequest;
import org.sopt.request.UpdatePostRequest;
import org.sopt.response.PostResponse;
import org.sopt.response.Response;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * 콘솔을 통해 사용자 입력을 받고 처리 결과를 출력합니다.
 */
public class PostView {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * 게시판에서 수행할 수 있는 작업 목록을 출력합니다.
     */
    public void printMenu() {
        System.out.println("\n=== 게시판 ===");
        System.out.println("1. 게시글 작성");
        System.out.println("2. 게시글 목록 조회");
        System.out.println("3. 게시글 단건 조회");
        System.out.println("4. 게시글 수정");
        System.out.println("5. 게시글 삭제");
        System.out.println("6. 종료");
    }

    /**
     * 게시글 목록 조회 응답을 출력합니다.
     *
     * @param response 게시글 목록 조회 응답
     */
    public void printPosts(Response<List<PostResponse>> response) {
        if (response.message() != null) {
            System.out.println(response.message());
        }

        if (!response.success()) {
            return;
        }

        for (PostResponse post : response.data()) {
            System.out.println("[id: " + post.id() + "] " + post.title());
        }
    }

    /**
     * 게시글 단건 조회 응답을 출력합니다.
     *
     * @param response 게시글 단건 조회 응답
     */
    public void printPost(Response<PostResponse> response) {
        if (!response.success()) {
            System.out.println(response.message());
            return;
        }

        PostResponse post = response.data();

        System.out.println("\n=== 게시글 ===");
        System.out.println("제목: " + post.title());
        System.out.println("내용: " + post.content());
        System.out.println("카테고리: " + post.category());
        System.out.println("작성자: " + post.writer());
        System.out.print("생성: " + post.createdAt());

        if (!post.createdAt().equals(post.updatedAt())) {
            System.out.println(" | 수정: " + post.updatedAt());
        } else {
            System.out.println();
        }
    }

    /**
     * 서버가 반환한 공통 응답 메시지를 출력합니다.
     *
     * @param response 서버 응답
     */
    public void printResponse(Response<?> response) {
        System.out.println(response.message());
    }

    /**
     * 클라이언트에서 생성한 일반 메시지를 출력합니다.
     *
     * @param message 출력할 메시지
     */
    public void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * 사용자가 선택한 메뉴 명령을 읽습니다.
     *
     * @return 사용자가 입력한 메뉴 번호
     */
    public int readCommand() {
        return readInt("작업 선택: ");
    }

    /**
     * 게시글 조회·수정·삭제에 사용할 게시글 ID를 읽습니다.
     *
     * @param message 입력 안내 메시지
     * @return 사용자가 입력한 게시글 ID
     */
    public long readPostNumber(String message) {
        return readLong(message);
    }

    /**
     * 게시글 생성에 필요한 입력을 모아 생성 요청을 반환합니다.
     *
     * @return 게시글 생성 요청
     */
    public CreatePostRequest readCreatePostRequest() {
        return new CreatePostRequest(
                readTitle(),
                readContent(),
                readCategory(),
                readWriter()
        );
    }

    /**
     * 게시글 수정에 필요한 입력을 모아 수정 요청을 반환합니다.
     *
     * @return 게시글 수정 요청
     */
    public UpdatePostRequest readUpdatePostRequest() {
        return new UpdatePostRequest(
                readNewTitle(),
                readNewContent(),
                readNewCategory()
        );
    }

    private String readTitle() {
        System.out.print("제목: ");
        return scanner.nextLine();
    }

    private String readContent() {
        System.out.print("내용: ");
        return scanner.nextLine();
    }

    private String readWriter() {
        System.out.print("작성자: ");
        return scanner.nextLine();
    }

    private Category readCategory() {
        while (true) {
            printCategoryMenu();

            int categoryNumber = readInt("카테고리: ");

            try {
                return Category.fromNumber(categoryNumber);
            } catch (CustomException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Optional<String> readNewTitle() {
        System.out.print("제목(제목을 변경하지 않으려면 Enter): ");
        String title = scanner.nextLine();

        if (title.isBlank()) {
            return Optional.empty();
        }

        return Optional.of(title);
    }

    private Optional<String> readNewContent() {
        System.out.print("내용(내용을 변경하지 않으려면 Enter): ");
        String content = scanner.nextLine();

        if (content.isBlank()) {
            return Optional.empty();
        }

        return Optional.of(content);
    }

    private Optional<Category> readNewCategory() {
        while (true) {
            printCategoryMenu();
            System.out.print("카테고리(카테고리를 변경하지 않으려면 Enter): ");

            String input = scanner.nextLine();

            if (input.isBlank()) {
                return Optional.empty();
            }

            try {
                int categoryNumber = Integer.parseInt(input);
                return Optional.of(Category.fromNumber(categoryNumber));
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            } catch (CustomException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int readInt(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }

    private long readLong(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();

            try {
                return Long.parseLong(input);
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }

    private void printCategoryMenu() {
        System.out.println("=== 카테고리 ===");

        for (Category category : Category.values()) {
            System.out.println(category.getNumber() + ". " + category.getName());
        }
        System.out.println("==============");
    }
}