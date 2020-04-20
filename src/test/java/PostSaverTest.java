import com.google.gson.JsonParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PostSaverTest {

    private static final String TEST_FILES_DIRECTORY = "posts_test";

    private PostSaver postSaver;
    private File tempFile;

    @Before
    public void before() {
        postSaver = new PostSaver();
        tempFile = new File(TEST_FILES_DIRECTORY);
    }

    @After
    public void after() throws IOException {
        Files.walk(Path.of(TEST_FILES_DIRECTORY))
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    public void shouldSavePostsInJsonFiles() {
        // given
        final var posts = PostsProvider.providePosts();

        // when
        postSaver.saveAsJsonFiles(TEST_FILES_DIRECTORY, posts);

        // then
        assertEquals(posts.size(), Objects.requireNonNull(tempFile.listFiles()).length);
        posts.forEach(post -> {
            assertEquals(1, Objects.requireNonNull(tempFile.listFiles((file, s) ->
                    s.equals(String.format("%s.json", post.getId())))).length);
        });
    }

    @Test
    public void shouldCleanDirectoryAndSaveNewFile() throws IOException {
        // given
        final var posts = PostsProvider.providePosts();
        final var tempFile = new File(TEST_FILES_DIRECTORY);
        final var oldPostIdAndUserId = 1;
        final var newPosIdAndUserId = 2;

        final var newPostText = PostsProvider.FIRST_POST_TEXT
                .replaceAll(String.valueOf(oldPostIdAndUserId), String.valueOf(newPosIdAndUserId));
        final var newPost = new Post(JsonParser.parseString(newPostText));
        final var newPosts = Collections.singletonList(newPost);

        // when
        postSaver.saveAsJsonFiles(TEST_FILES_DIRECTORY, posts);
        postSaver.saveAsJsonFiles(TEST_FILES_DIRECTORY, newPosts);

        // then
        assertEquals(newPosts.size(), Objects.requireNonNull(tempFile.listFiles()).length);
        final var fileText = Files.readString(Objects.requireNonNull(tempFile.listFiles())[0].toPath());
        final var postJson = JsonParser.parseString(fileText);

        assertEquals(newPosIdAndUserId, postJson.getAsJsonObject().get("id").getAsInt());
        assertNotEquals(posts.get(0).getJsonElement(), postJson);
    }
}
