package comp3350.chefsnotes.business;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import comp3350.chefsnotes.objects.Recipe;
import comp3350.chefsnotes.objects.RecipeExample;
import comp3350.chefsnotes.objects.TagExistenceException;
import comp3350.chefsnotes.persistence.FakeDBMS;
import comp3350.chefsnotes.persistence.FakeTagDB;

public class ITagHandlerTest {
    private ITagHandler handler;

    @Before
    public void setup() {
        handler = new TagHandler(new FakeTagDB(), new FakeDBMS());
    }

    @Test
    public void testEmAll() {
        Recipe r = new RecipeExample();
        assertEquals(0, handler.fetchTags().length);
        handler.addTagToRecipe(r, "Foo");
        assertEquals(true, r.getTags().contains("Foo"));
        assertEquals(1, handler.fetchTags().length);
        assertEquals("Foo", handler.fetchTags()[0]);

        handler.removeTagFromRecipe(r, "Foo");
        try {
            handler.deleteTag("Foo");
        }catch (TagExistenceException e)
        {
            fail("unexpected exception");
        }

        assertEquals(0, handler.fetchTags().length);

        assertThrows(TagExistenceException.class, () -> handler.deleteTag("Foo"));

        handler.addTagToRecipe(r, "1111");
        handler.addTagToRecipe(r, "1111");
    }

}

