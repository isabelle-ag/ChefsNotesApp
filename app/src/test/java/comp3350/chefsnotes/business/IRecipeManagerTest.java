package comp3350.chefsnotes.business;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import comp3350.chefsnotes.objects.Direction;
import comp3350.chefsnotes.objects.Ingredient;
import comp3350.chefsnotes.objects.Recipe;
import comp3350.chefsnotes.objects.RecipeExistenceException;
import comp3350.chefsnotes.persistence.DBMSTools;
import comp3350.chefsnotes.persistence.FakeDBMS;

public class IRecipeManagerTest {

    DBMSTools db;
    IRecipeManager manager;

    @Before
    public void setup() {
        db = new FakeDBMS();
        manager = new RecipeManager(db);
    }


    @Test
    public void testNewRecipe() {
        Recipe myRecipe = null;
        try {
            myRecipe = manager.newRecipe("test1");
            assertNotNull(myRecipe);
        }
        catch (RecipeExistenceException e){fail();}
        Recipe R1 = db.getRecipe("test1");
        assertEquals(myRecipe, R1);
    }

    @Test
    public void testDelRecipe() {
        Recipe myRecipe = null;
        try {
            myRecipe = manager.newRecipe("testDel");
        }
        catch (RecipeExistenceException e){fail();}

        assertNotNull(db.getRecipe("testDel"));
        try {
            manager.delRecipe(myRecipe);
        }
        catch (RecipeExistenceException e){fail();}

        assertNull(db.getRecipe("testDel"));

        Recipe finalMyRecipe = myRecipe;
        assertThrows(RecipeExistenceException.class, () -> manager.delRecipe(finalMyRecipe));
    }


    @Test
    public void testRenameRecipe() {
        Recipe myRecipe = null;
        Recipe collision = null;
        try {
            myRecipe = manager.newRecipe("testName1");
            collision = manager.newRecipe("testNameCol");
        }
        catch (RecipeExistenceException e){fail();}
        try {
            manager.renameRecipe(myRecipe, "testName2");
        }
        catch (RecipeExistenceException e){fail();}

        assertEquals(myRecipe.getTitle(), "testName2");
        assertEquals(db.getRecipe("testName2"), myRecipe);

        Recipe finalMyRecipe = myRecipe;
        assertThrows(RecipeExistenceException.class, () -> manager.renameRecipe(finalMyRecipe, "testNameCol"));

    }

    @Test
    public void testSaveButton() {
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        ArrayList<Direction> directions = new ArrayList<Direction>();

        ingredients.add(new Ingredient("testIng1", 0.5, "testUnit1"));
        ingredients.add(new Ingredient("testIng2", 1, 4, "testUnit2"));

        directions.add(new Direction("dir1", 1));
        directions.add(new Direction("dir2", 4));
        directions.add(new Direction("dir3", "dir3b", 2));

        try {
            manager.saveButton("testSave", ingredients, directions);
        }
        catch (RecipeExistenceException e){fail();}

        Recipe myRecipe = db.getRecipe("testSave");
        assertNotNull(myRecipe);

        //ensure that every ingredient in myRecipe is in the ingredient list we passed
        for (Ingredient i : myRecipe.getIngredients()) {
            assertTrue(ingredients.remove(i));
        }
        //ensure that every ingredient we passed was in myRecpie
        assertTrue(ingredients.isEmpty());

        //ditto for directions
        for (Direction d : myRecipe.getDirections()) {
            assertTrue(directions.remove(d));
        }
        assertTrue(directions.isEmpty());
    }

    @Test
    public void testCopyRecipe() throws RecipeExistenceException{
        Recipe R1 = manager.newRecipe("test");
        R1.addDirection(new Direction("foo"));

        Recipe R2 = manager.copyRecipe(R1, "bar");

        System.out.println(R2);

        assertEquals("foo", R2.getDirections()[0].getText());
    }
}