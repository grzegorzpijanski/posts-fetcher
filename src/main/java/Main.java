import java.net.http.HttpClient;

public class Main {

    private static final String FILES_DIRECTORY = "posts";

    public static void main(String[] args) {
        final var apiConsumer = new ApiConsumer(HttpClient.newHttpClient());
        final var postsSaver = new PostsSaver();

        apiConsumer.fetch()
                .map(PostsMapper::map)
                .ifPresent(posts -> postsSaver.saveAsJsonFiles(FILES_DIRECTORY, posts));
    }
}
