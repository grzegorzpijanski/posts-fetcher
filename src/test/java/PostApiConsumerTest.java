import org.junit.Before;
import org.junit.Test;

import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostApiConsumerTest {

    private static final String POSTS_URL = "https://jsonplaceholder.typicode.com/posts";

    final HttpClient httpClient = mock(HttpClient.class);
    PostApiConsumer postApiConsumer;

    @Before
    public void before() {
        postApiConsumer = new PostApiConsumer(httpClient);
    }

    @Test
    public void shouldReturnExpectedPosts() throws IOException, InterruptedException {
        // given
        final HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(POSTS_URL))
                .version(HttpClient.Version.HTTP_2)
                .build();

        final var responseStatusCode = 200;

        final List<Post> expectedPosts = PostsProvider.providePosts();
        final var responseBody =  String.format("[%s,%s,%s]", PostsProvider.FIRST_POST_TEXT,
                PostsProvider.SECOND_POST_TEXT, PostsProvider.THIRD_POST_TEXT);

        final var response = new CustomHttpResponse(responseStatusCode, responseBody);

        // when
        when(httpClient.send(eq(httpRequest), any(HttpResponse.BodyHandler.class))).thenReturn(response);
        final Optional<List<Post>> result = postApiConsumer.fetch();

        // then
        assert(result.isPresent());
        assert(result.get().containsAll(expectedPosts));
    }

    @Test
    public void shouldReturnEmptyWhenBadResponse() throws IOException, InterruptedException {
        // given
        final HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(POSTS_URL))
                .version(HttpClient.Version.HTTP_2)
                .build();

        final var responseStatusCode = 500;
        final var response = new CustomHttpResponse(responseStatusCode);

        // when
        when(httpClient.send(eq(httpRequest), any(HttpResponse.BodyHandler.class))).thenReturn(response);
        final Optional<List<Post>> result = postApiConsumer.fetch();

        // then
        assert(result.isEmpty());
    }

    @Test
    public void shouldReturnEmptyWhenThrowException() throws IOException, InterruptedException {
        // given
        final HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(POSTS_URL))
                .version(HttpClient.Version.HTTP_2)
                .build();

        final var exception = new IOException("Error message");

        // when
        when(httpClient.send(eq(httpRequest), any(HttpResponse.BodyHandler.class))).thenThrow(exception);
        final Optional<List<Post>> result = postApiConsumer.fetch();

        // then
        assert(result.isEmpty());
    }

    static class CustomHttpResponse implements HttpResponse<String> {

        private final int statusCode;

        private String body;

        public CustomHttpResponse(final int statusCode) {
            this.statusCode = statusCode;
        }

        public CustomHttpResponse(final int statusCode, final String body) {
            this.statusCode = statusCode;
            this.body = body;
        }

        @Override
        public int statusCode() {
            return statusCode;
        }

        @Override
        public HttpRequest request() {
            return null;
        }

        @Override
        public Optional<HttpResponse<String>> previousResponse() {
            return Optional.empty();
        }

        @Override
        public HttpHeaders headers() {
            return null;
        }

        @Override
        public String body() {
            return body;
        }

        @Override
        public Optional<SSLSession> sslSession() {
            return Optional.empty();
        }

        @Override
        public URI uri() {
            return null;
        }

        @Override
        public HttpClient.Version version() {
            return null;
        }
    }
}
