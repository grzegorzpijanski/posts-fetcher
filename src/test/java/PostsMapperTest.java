import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class PostsMapperTest {

    @Test
    public void shouldParsePosts() {
        // given
        final var jsonArray = PostsProvider.provideJsonArray();

        // when
        final List<Post> result = PostsMapper.map(jsonArray);

        // then
        assertEquals(jsonArray.get(0).getAsJsonObject(), result.get(0).getJsonElement());
        assertEquals(jsonArray.get(0).getAsJsonObject().get("id").getAsInt(), result.get(0).getId());
        assertEquals(jsonArray.get(1).getAsJsonObject(), result.get(1).getJsonElement());
        assertEquals(jsonArray.get(1).getAsJsonObject().get("id").getAsInt(), result.get(1).getId());
        assertEquals(jsonArray.get(2).getAsJsonObject(), result.get(2).getJsonElement());
        assertEquals(jsonArray.get(2).getAsJsonObject().get("id").getAsInt(), result.get(2).getId());
    }
}
