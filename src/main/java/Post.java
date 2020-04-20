import com.google.gson.JsonElement;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class Post {

    private static final String POST_ID = "id";

    private final JsonElement jsonElement;

    public Post(final JsonElement jsonElement) {
        this.jsonElement = jsonElement;
    }

    public int getId() {
        return jsonElement.getAsJsonObject().get(POST_ID).getAsInt();
    }

    public JsonElement getJsonElement() {
        return jsonElement;
    }
}
