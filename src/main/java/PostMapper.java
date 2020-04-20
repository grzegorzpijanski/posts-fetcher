import com.google.gson.JsonElement;

public final class PostMapper {

    public static Post toPost(final JsonElement jsonElement) {
        final var jsonObject = jsonElement.getAsJsonObject();

        return new Post(jsonObject);
    }
}
