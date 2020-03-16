import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public final class ApiConsumer {

    private static final String POSTS_URL = "https://jsonplaceholder.typicode.com/posts";

    private HttpClient httpClient;

    public ApiConsumer(final HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Optional<JsonArray> fetch() {
        final HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(POSTS_URL))
                .version(HttpClient.Version.HTTP_2)
                .build();
        try {
            final HttpResponse<String> httpResponse = httpClient
                    .send(httpRequest, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (isPositive(httpResponse.statusCode())) {
                return Optional.of(JsonParser.parseString(httpResponse.body()).getAsJsonArray());
            }
        } catch (final IOException | InterruptedException e) {
            System.out.println(String.format("An error occurred during API request execution: \n%s", e.getMessage()));
        }
        return Optional.empty();
    }

    private boolean isPositive(final int statusCode) {
        return !(statusCode < 200 || statusCode > 299);
    }
}
