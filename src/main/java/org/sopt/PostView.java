// PostView
package org.sopt;

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

    public int readCommand() {
        System.out.print("선택: ");
        return Integer.parseInt(scanner.nextLine());
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
        System.out.println("작성자: ");
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
        printCategoryMenu();
        System.out.print("카테고리: ");

        int categoryNumber = Integer.parseInt(scanner.nextLine());
        return Category.fromNumber(categoryNumber);
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
        printCategoryMenu();
        System.out.print("카테고리(카테고리를 변경하지 않으려면 Enter): ");

        String input = scanner.nextLine();

        if (input.isBlank()) {
            return Optional.empty();
        }

        int categoryNumber = Integer.parseInt(input);
        return Optional.of(Category.fromNumber(categoryNumber));
    }

    public int readPostNumber(String message) {
        System.out.print(message);
        return Integer.parseInt(scanner.nextLine());
    }

    public void printPosts(List<Post> posts) {
        for (int i=0; i<posts.size(); i++) {
            System.out.println((i + 1) + ". " + posts.get(i).getTitle());
        }
    }

    public void printPost(Post post) {
        System.out.println("\n=== 게시글 ===");
        System.out.println("제목: " + post.getTitle());
        System.out.println("내용: " + post.getContent());
        System.out.println("카테고리: " + post.getCategory().getName());
        System.out.println("작성자: " + post.getWriter());
        System.out.print("생성: " + post.getCreatedAt());

        if (!post.getCreatedAt().equals(post.getUpdatedAt())) {
            System.out.println(" | 수정: " + post.getUpdatedAt());
        } else {
            System.out.println();
        }
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
