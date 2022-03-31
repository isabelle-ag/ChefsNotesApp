package comp3350.chefsnotes.business;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import comp3350.chefsnotes.R;
import comp3350.chefsnotes.objects.Direction;
import comp3350.chefsnotes.objects.Ingredient;
import comp3350.chefsnotes.objects.Recipe;
import comp3350.chefsnotes.objects.RecipeExistenceException;
import comp3350.chefsnotes.persistence.DBMSTools;
import comp3350.chefsnotes.persistence.RecipePersistence;
import comp3350.chefsnotes.utils.TestUtils;

public class RecipeStuffIT {
    private IRecipeManager manager;
    private IRecipeFetcher fetcher;
    private File tempDB;

    //shamelessly copied from the sample project
    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        final DBMSTools persistence = new RecipePersistence(this.tempDB.getAbsolutePath().replace(".script", ""));
        this.manager = new RecipeManager(persistence);
        this.fetcher = new RecipeFetcher(persistence);
    }

    @Test
    public void testFetchAll() throws RecipeExistenceException {
        Recipe[] recipes = fetcher.getRecipesByText("");
        assertEquals(0, recipes.length);


        Recipe R1 = manager.newRecipe("foobar");
        Recipe R2 = manager.newRecipe("baz");

        recipes = fetcher.getRecipesByText("");
        assertEquals(2, recipes.length);
        for (Recipe r:recipes) {
            assertTrue(r.equals(R1) || r.equals(R2));
        }

        return;
    }

    @Test
    public void testSearch() throws RecipeExistenceException
    {
        Recipe R1 = manager.newRecipe("foobar");
        Recipe R2 = manager.newRecipe("baz");
        Recipe R3 = manager.newRecipe("foobazbar");
        Recipe R4 = manager.newRecipe("b1a2z3");

        ArrayList<Recipe> recipes = new ArrayList<Recipe>(Arrays.asList(fetcher.getRecipesByText("baz")));
        assertEquals(2, recipes.size());
        assertFalse(recipes.contains(R1));
        assertTrue(recipes.contains(R2));
        assertTrue(recipes.contains(R3));
        assertFalse(recipes.contains(R4));
    }


    @Test
    public void testDeletion() throws RecipeExistenceException
    {
        Recipe R1 = manager.newRecipe("foobar");
        Recipe R2 = manager.newRecipe("baz");
        Recipe R3 = manager.newRecipe("foobazbar");
        Recipe R4 = manager.newRecipe("b1a2z3");

        manager.delRecipe(R2);

        assertNull(fetcher.getRecipeByName("baz"));

        assertThrows(RecipeExistenceException.class, () -> manager.delRecipe(R2));
    }

    @Test
    public void testRename() throws RecipeExistenceException
    {
        Recipe R1 = manager.newRecipe("foobar");
        assertNotNull(fetcher.getRecipeByName("foobar"));
        assertNull(fetcher.getRecipeByName("baz"));

        manager.renameRecipe(R1, "baz");
        assertNull(fetcher.getRecipeByName("foobar"));
        assertNotNull(fetcher.getRecipeByName("baz"));
    }


    @Test
    public void testFetchLatest() throws RecipeExistenceException
    {
        Recipe R1 = manager.newRecipe("foo");
        assertEquals(R1, fetcher.getRecentRecipe());

        Recipe R2 = manager.newRecipe("bar");
        assertEquals(R2, fetcher.getRecentRecipe());
    }

    @Test
    public void testSaveButton() throws RecipeExistenceException
    {
        Recipe R1 = manager.newRecipe("foo");
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        ArrayList<Direction> directions = new ArrayList<Direction>();

        ingredients.add(new Ingredient("iName", 50, "cup"));

        directions.add(new Direction("dText"));

        assertEquals(0, R1.getIngredients().length);
        assertEquals(0, R1.getDirections().length);

        manager.saveButton(R1.getTitle(), ingredients, directions);

        R1 = fetcher.getRecipeByName(R1.getTitle());


        assertEquals(1, R1.getIngredients().length);
        assertEquals(1, R1.getDirections().length);
    }

    @Test
    public void testCopyRecipe() throws RecipeExistenceException
    {
        Recipe R1 = manager.newRecipe("foo");
        R1.addDirection(new Direction("bar"));

        manager.saveButton(R1.getTitle(), new ArrayList<Ingredient>(Arrays.asList(R1.getIngredients())), new ArrayList<Direction>(Arrays.asList(R1.getDirections())));

        Recipe R2 = manager.copyRecipe(R1, null);
        Recipe R3 = manager.copyRecipe(R1, "baz");
        Recipe R4 = manager.copyRecipe(R1, "foo");

        assertEquals(R2, fetcher.getRecipeByName("foo-copy"));
        assertEquals(R3, fetcher.getRecipeByName("baz"));
        assertEquals(R4, fetcher.getRecipeByName("foo-copy-copy"));

        assertEquals(R1.getDirections()[0], R3.getDirections()[0]);

    }

    @After
    public void tearDown() {
        this.tempDB.delete();
    }
}
