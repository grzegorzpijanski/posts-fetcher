import com.google.gson.JsonArray;
import java.util.ArrayList;
import java.util.List;

public final class PostsMapper {

    public static List<Post> map(final JsonArray jsonArray) {
        final List<Post> posts = new ArrayList<>();

        jsonArray.forEach(jsonElement -> {
            final var jsonObject = jsonElement.getAsJsonObject();
            posts.add(new Post(jsonObject));
        });

        return posts;
    }
}
