import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class PostApiConsumer {

    private static final String POSTS_URL = "https://jsonplaceholder.typicode.com/posts";

    private final HttpClient httpClient;

    public PostApiConsumer(final HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Optional<List<Post>> fetch() {
        final HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(POSTS_URL))
                .version(HttpClient.Version.HTTP_2)
                .build();

        try {
            final HttpResponse<String> httpResponse = httpClient
                    .send(httpRequest, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (isSuccess(httpResponse.statusCode())) {
                final var jsonArray = JsonParser.parseString(httpResponse.body()).getAsJsonArray();

                final List<Post> posts = StreamSupport.stream(jsonArray.spliterator(), true)
                        .map(JsonElement::getAsJsonObject)
                        .map(PostMapper::toPost)
                        .collect(Collectors.toList());

                return Optional.of(posts);
            }
        } catch (final IOException | InterruptedException e) {
            System.out.println(String.format("An error occurred during API request execution: \n%s", e.getMessage()));
        }

        return Optional.empty();
    }

    private boolean isSuccess(final int statusCode) {
        return statusCode >= 200 && statusCode < 226;
    }
}
