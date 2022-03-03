package comp3350.chefsnotes.business;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
        catch (RecipeExistenceException e){fail();};
        Recipe R1 = db.getRecipe("test1");
        assertEquals(myRecipe, R1);

        assertThrows(RecipeExistenceException.class, () -> manager.newRecipe("test1"));
    }

    @Test
    public void testDelRecipe() {
        Recipe myRecipe = null;
        try {
            myRecipe = manager.newRecipe("testDel");
        }
        catch (RecipeExistenceException e){fail();};

        assertNotNull(db.getRecipe("testDel"));
        try {
            manager.delRecipe(myRecipe);
        }
        catch (RecipeExistenceException e){fail();};

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
        catch (RecipeExistenceException e){fail();};
        try {
            manager.renameRecipe(myRecipe, "testName2");
        }
        catch (RecipeExistenceException e){fail();};

        assertEquals(myRecipe.getTitle(), "testName2");
        assertEquals(db.getRecipe("testName2"), myRecipe);

        Recipe finalMyRecipe = myRecipe;
        assertThrows(RecipeExistenceException.class, () -> manager.renameRecipe(finalMyRecipe, "testNameCol"));

    }

    @Test
    public void testSaveButton() {
        //TODO
    }
}