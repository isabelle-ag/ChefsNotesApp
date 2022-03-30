package comp3350.chefsnotes.business;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import comp3350.chefsnotes.objects.Recipe;
import comp3350.chefsnotes.objects.SampleRecipe;
import comp3350.chefsnotes.objects.TagExistenceException;
import comp3350.chefsnotes.persistence.FakeTagDB;

public class ITagHandlerTest {
    private ITagHandler handler;

    @Before
    public void setup() {
        handler = new TagHandler(new FakeTagDB());
    }

    @Test
    public void testEmAll() {
        Recipe r = new SampleRecipe();
        assertEquals(0, handler.fetchTags().length);
        handler.addTagToRecipe(r, "foo");
        assertEquals(1, handler.fetchTags().length);
        assertEquals("foo", handler.fetchTags()[0]);

        try {
            handler.removeTagFromRecipe(r, "foo");
        } catch (TagExistenceException e) {
            fail("unexpected tagexistence exception");
        }
        assertEquals(0, handler.fetchTags().length);

        assertThrows(TagExistenceException.class, () -> handler.removeTagFromRecipe(r, "foo"));
    }

}

