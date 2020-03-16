import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public final class PostsSaver {

    private static final String FILE_PATH = "/%s.json";

    public void saveAsJsonFiles(final String directory, final List<Post> posts) {
        manageDirectory(directory);
        posts.forEach(post -> writeFile(directory, String.valueOf(post.getId()), post.getJsonElement().toString()));
    }

    private void manageDirectory(final String directory) {
        Optional<File[]> files = Optional.ofNullable(new File(directory).listFiles());

        if (files.isPresent()) {
            for (File file : files.get()) {
                file.delete();
            }
        } else {
            createDirectory(directory);
        }
    }

    private void createDirectory(final String directory) {
            try {
                Files.createDirectories(Path.of(directory));
            } catch (final IOException e ) {
                System.out.println(String.format("An error occurred during creating a directory %s: \n%s",
                        directory, e.getMessage()));
            }
    }

    private void writeFile(final String directory, final String title, final String content) {
        final var pathToFile = Paths.get(directory + String.format(FILE_PATH, title));
        try {
            Files.createDirectories(pathToFile.getParent());
            Files.writeString(pathToFile, content, StandardCharsets.UTF_8);
        } catch (final IOException e) {
            System.out.println(String.format("An error occurred during writing post into %s: \n%s",
                    pathToFile.toString(), e.getMessage()));
        }
    }
}
