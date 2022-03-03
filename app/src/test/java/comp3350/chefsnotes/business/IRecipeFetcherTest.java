package comp3350.chefsnotes.business;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import comp3350.chefsnotes.R;
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
}