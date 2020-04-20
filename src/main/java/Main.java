import java.net.http.HttpClient;

public class Main {

    private static final String FILES_DIRECTORY = "posts";

    public static void main(String[] args) {
        final var apiConsumer = new PostApiConsumer(HttpClient.newHttpClient());
        final var postsSaver = new PostSaver();

        apiConsumer.fetch()
                .ifPresent(posts -> postsSaver.saveAsJsonFiles(FILES_DIRECTORY, posts));
    }
}
