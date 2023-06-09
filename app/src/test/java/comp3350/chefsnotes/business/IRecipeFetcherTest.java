package comp3350.chefsnotes.business;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import comp3350.chefsnotes.R;
import comp3350.chefsnotes.objects.Ingredient;
import comp3350.chefsnotes.objects.Recipe;
import comp3350.chefsnotes.persistence.DBMSTools;
import comp3350.chefsnotes.persistence.FakeDBMS;

public class IRecipeFetcherTest {
    static DBMSTools db;
    static IRecipeFetcher fetcher;

    @Before
    public void setup() {
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

    @Test
    public void testFilterRecipesByTag() {
        Recipe foo = db.getRecipe("foo");
        Recipe barfoo = db.getRecipe("barfoo");
        Recipe bar = db.getRecipe("bar");
        bar.addTag("barTag");
        foo.addTag("fooTag");
        barfoo.addTag("barTag");
        barfoo.addTag("fooTag");

        ArrayList<String> included = new ArrayList<String>();
        ArrayList<String> excluded = new ArrayList<String>();

        assertEquals(6, fetcher.filterRecipesByTags(included.toArray(new String[0]), excluded.toArray(new String[0])).length);

        included.add("fooTag");

        assertEquals(2, fetcher.filterRecipesByTags(included.toArray(new String[0]), excluded.toArray(new String[0])).length);


        excluded.add("barTag");



        ArrayList<Recipe> fetched = new ArrayList<Recipe>(Arrays.asList(fetcher.filterRecipesByTags(included.toArray(new String[0]), excluded.toArray(new String[0]))));
        System.out.println(fetched);
        assertTrue(fetched.contains(db.getRecipe("foo")));
        assertFalse(fetched.contains(db.getRecipe("barfoo")));
        assertFalse(fetched.contains(db.getRecipe("bar")));

    }


    @Test
    public void testGetRecipesByIngredient() {
        Recipe foo = db.getRecipe("foo");
        Recipe barfoo = db.getRecipe("barfoo");
        Recipe bar = db.getRecipe("bar");

        assertEquals(0, fetcher.getRecipesByIngredient("foo").length);


        foo.addIngredient(new Ingredient("foo"));

        assertEquals(1, fetcher.getRecipesByIngredient("foo").length);

        barfoo.addIngredient(new Ingredient("barfoo"));

        assertEquals(1, fetcher.getRecipesByIngredient("foo").length);
        assertEquals(0, fetcher.getRecipesByIngredient("bar").length);
        assertEquals(1, fetcher.getRecipesByIngredient("barfoo").length);

        bar.addIngredient(new Ingredient("bar"));
        bar.addIngredient(new Ingredient("foobar"));
        bar.addIngredient(new Ingredient("barfoo"));
        assertEquals(1, fetcher.getRecipesByIngredient("bar;foobar;barfoo").length);
        assertEquals(6, fetcher.getRecipesByIngredient("").length);
    }
}
