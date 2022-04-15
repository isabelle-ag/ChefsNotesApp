package comp3350.chefsnotes.business;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import comp3350.chefsnotes.objects.Recipe;
import comp3350.chefsnotes.objects.RecipeExample;
import comp3350.chefsnotes.objects.RecipeExistenceException;
import comp3350.chefsnotes.objects.TagExistenceException;
import comp3350.chefsnotes.persistence.DBMSTools;
import comp3350.chefsnotes.persistence.RecipePersistence;
import comp3350.chefsnotes.persistence.TagDBMSTools;
import comp3350.chefsnotes.persistence.TagPersistence;
import comp3350.chefsnotes.utils.TestUtils;

public class TagStuffIT {

    private IRecipeManager manager;
    private IRecipeFetcher fetcher;
    private ITagHandler handler;
    private File tempDB;

    //shamelessly copied from the sample project
    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        final DBMSTools persistence = new RecipePersistence(this.tempDB.getAbsolutePath().replace(".script", ""));
        final TagDBMSTools tagPersist = new TagPersistence(this.tempDB.getAbsolutePath().replace(".script", ""));
        this.manager = new RecipeManager(persistence);
        this.fetcher = new RecipeFetcher(persistence);
        this.handler = new TagHandler(tagPersist, persistence);
    }

    //shamelessly copypasted from my unit tests
    @Test
    public void testTagHandler() {
        Recipe r = new RecipeExample();
        assertEquals(0, handler.fetchTags().length);
        handler.addTagToRecipe(r, "foo");
        assertEquals(true, r.getTags().contains("foo"));
        assertEquals(1, handler.fetchTags().length);
        assertEquals("foo", handler.fetchTags()[0]);

        handler.removeTagFromRecipe(r, "foo");
        try {
            handler.deleteTag("foo");
        } catch (TagExistenceException e) {
            fail("unexpected exception");
        }

        assertEquals(0, handler.fetchTags().length);

        assertThrows(TagExistenceException.class, () -> handler.deleteTag("foo"));
    }

    @Test
    public void testFilterRecipesByTag() throws RecipeExistenceException
    {
        Recipe foo = manager.newRecipe("foo");
        Recipe barfoo = manager.newRecipe("barfoo");
        Recipe bar = manager.newRecipe("bar");

        String[] included = {"fooTag"};
        String[] excluded = {"barTag"};


        handler.addTagToRecipe(bar, "barTag");
        handler.addTagToRecipe(barfoo, "barTag");
        handler.addTagToRecipe(barfoo, "fooTag");
        handler.addTagToRecipe(foo, "fooTag");

        ArrayList<Recipe> fetched = new ArrayList<Recipe>(Arrays.asList(fetcher.filterRecipesByTags(included, excluded)));
        System.out.println(fetched);
        assertTrue(fetched.contains(fetcher.getRecipeByName("foo")));
        assertFalse(fetched.contains(fetcher.getRecipeByName("barfoo")));
        assertFalse(fetched.contains(fetcher.getRecipeByName("bar")));

        assertEquals(2, handler.fetchTags().length);
    }

    @After
    public void tearDown() {
        this.tempDB.delete();
    }

}
