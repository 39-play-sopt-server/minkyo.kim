package org.sopt;

public class Main {
    public static void main(String[] args) {
        PostView view = new PostView();
        PostRepository repository = new PostRepository();
        PostService service = new PostService(repository);
        PostController controller = new PostController(view, service);

        controller.run();
    }
}