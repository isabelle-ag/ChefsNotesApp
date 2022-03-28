package comp3350.chefsnotes.business;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import comp3350.chefsnotes.R;
import comp3350.chefsnotes.objects.Recipe;
import comp3350.chefsnotes.persistence.DBMSTools;
import comp3350.chefsnotes.persistence.FakeDBMS;

public class IRecipeFetcherTest {
    static DBMSTools db;
    static IRecipeFetcher fetcher;

    @BeforeClass
    public static void setup() {
        db = new FakeDBMS();
        db.createRecipe("test1");
        db.createRecipe("test2");
        db.createRecipe("foo");
        db.createRecipe("barfoo");
        db.createRecipe("bar");
        db.createRecipe("testRECENT");

        fetcher = new RecipeFetcher(db);

    }

    @Test
    public void testGetRecentRecipe() {
        IRecipeFetcher emptyfetcher = new RecipeFetcher(new FakeDBMS());
        assertNull(emptyfetcher.getRecentRecipe());
        assertEquals(fetcher.getRecentRecipe().getTitle(), "testRECENT");

    }

    @Test
    public void testGetRecipeByName() {
        assertNull(fetcher.getRecipeByName("testFAIL"));

        assertEquals(fetcher.getRecipeByName("test1").getTitle(), "test1");

    }

    @Test
    public void testGetRecipesByText() {
        ArrayList<Recipe> fetched = new ArrayList<Recipe>(Arrays.asList(fetcher.getRecipesByText("foo")));

        assertTrue(fetched.contains(db.getRecipe("foo")));
        assertTrue(fetched.contains(db.getRecipe("barfoo")));
        assertFalse(fetched.contains(db.getRecipe("bar")));
    }
}