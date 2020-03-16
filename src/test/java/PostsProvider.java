import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.Arrays;
import java.util.List;

public final class PostsProvider {

    public PostsProvider() throws IllegalAccessException {
        throw new IllegalAccessException("No need to create an instance of utility class");
    }

    public static final String FIRST_POST_TEXT = "{\"userId\":1,\"id\":1,\"title\":\"first post title\",\"body\":\"first post body\"}";
    public static final String SECOND_POST_TEXT = "{\"userId\":2,\"id\":2,\"title\":\"second post title\",\"body\":\"second post body\"}";
    public static final String THIRD_POST_TEXT = "{\"userId\":3,\"id\":3,\"title\":\"third post title\",\"body\":\"third post body\"}";

    public static JsonArray provideJsonArray() {
        final JsonArray jsonArray = new JsonArray(3);
        jsonArray.add(JsonParser.parseString(FIRST_POST_TEXT));
        jsonArray.add(JsonParser.parseString(SECOND_POST_TEXT));
        jsonArray.add(JsonParser.parseString(THIRD_POST_TEXT));

        return jsonArray;
    }

    public static List<Post> providePosts() {
        return Arrays.asList(new Post(JsonParser.parseString(FIRST_POST_TEXT)),
                new Post(JsonParser.parseString(SECOND_POST_TEXT)),
                new Post(JsonParser.parseString(THIRD_POST_TEXT)));
    }
}
