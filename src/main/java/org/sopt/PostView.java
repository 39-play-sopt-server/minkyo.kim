// PostView
package org.sopt;

import org.sopt.exception.CustomException;
import org.sopt.request.CreatePostRequest;
import org.sopt.request.UpdatePostRequest;
import org.sopt.response.PostResponse;
import org.sopt.response.Response;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PostView {
    private final Scanner scanner = new Scanner(System.in);

    public void printMenu() {
        System.out.println("\n=== 게시판 ===");
        System.out.println("1. 게시글 작성");
        System.out.println("2. 게시글 목록 조회");
        System.out.println("3. 게시글 단건 조회");
        System.out.println("4. 게시글 수정");
        System.out.println("5. 게시글 삭제");
        System.out.println("6. 종료");
    }

    public CreatePostRequest readCreatePostRequest() {
        return new CreatePostRequest(
                readTitle(),
                readContent(),
                readCategory(),
                readWriter()
        );
    }

    public UpdatePostRequest readUpdatePostRequest() {
        return new UpdatePostRequest(
                readNewTitle(),
                readNewContent(),
                readNewCategory()
        );
    }

    private int readInt(String message) {
        while(true) {
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
        while(true) {
            System.out.print(message);
            String input = scanner.nextLine();

            try {
                return Long.parseLong(input);
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }

    public int readCommand() {
        return readInt("작업 선택: ");
    }

    public String readTitle() {
        System.out.print("제목: ");
        return scanner.nextLine();
    }

    public String readContent() {
        System.out.print("내용: ");
        return scanner.nextLine();
    }

    public String readWriter() {
        System.out.print("작성자: ");
        return scanner.nextLine();
    }

    private void printCategoryMenu() {
        System.out.println("=== 카테고리 ===");

        for (Category category : Category.values()) {
            System.out.println(category.getNumber() + ". " + category.getName());
        }
        System.out.println("==============");
    }

    public Category readCategory() {
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

    public Optional<String> readNewTitle() {
        System.out.print("제목(제목을 변경하지 않으려면 Enter): ");
        String title = scanner.nextLine();

        if (title.isBlank()) {
            return Optional.empty();
        }

        return Optional.of(title);
    }

    public Optional<String> readNewContent() {
        System.out.print("내용(내용을 변경하지 않으려면 Enter): ");
        String content = scanner.nextLine();

        if (content.isBlank()) {
            return Optional.empty();
        }

        return Optional.of(content);
    }

    public Optional<Category> readNewCategory() {
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

    public long readPostNumber(String message) {
        return readLong(message);
    }

    public void printPosts(Response<List<PostResponse>> response) {
        if (response.message() != null) {
            System.out.println(response.message());
            return;
        }

        List<PostResponse> posts = response.data();
        for (PostResponse post : posts) {
            System.out.println("[id: " + post.id() + "] " + post.title());
        }
    }

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

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printResponse(Response<?> response) {
        System.out.println(response.message());
    }
}
