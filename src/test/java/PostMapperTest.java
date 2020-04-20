import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PostMapperTest {

    @Test
    public void shouldMapToPost() {
        // given
        final var jsonElement = PostsProvider.provideJsonElement();

        // when
        final Post result = PostMapper.toPost(jsonElement);

        // then
        assertEquals(jsonElement.getAsJsonObject(), result.getJsonElement());
        assertEquals(jsonElement.getAsJsonObject().get("id").getAsInt(), result.getId());
    }
}
