package comp3350.chefsnotes.persistence;

import comp3350.chefsnotes.objects.Recipe;
import comp3350.chefsnotes.objects.RecipeExample;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DBMSToolsTest {
    private DBMSTools testdb;

    @Before
    public void initialize(){
        testdb = new FakeDBMS();
    }

    @Test
    public void testCreateAndGetters(){
        String test1 = "Food";
        String test2 = "Drink";
        String nullTest = null;
        String[] results;
        Recipe[] rList;

        results = testdb.getRecipeNames();
        rList = testdb.getAllRecipes();
        assertEquals(results.length, 0);
        assertEquals(rList.length, 0);

        testdb.createRecipe(test1);
        testdb.createRecipe(test2);

        assertNotNull(testdb.getRecipe(test1));
        assertNotNull(testdb.getRecipe(test2));
        assertNull(testdb.getRecipe("Sludge"));
        assertNull(testdb.getRecipe(null));

        results = testdb.getRecipeNames();
        rList = testdb.getAllRecipes();
        assertEquals(results.length, 2);
        assertEquals(rList.length, 2);
        assertEquals(results[0],rList[0].getTitle());
        assertEquals(results[1],rList[1].getTitle());

    }

    @Test
    public void testSearch(){
        String test1 = "Sugar Cookies";
        String test2 = "Chocolate Chip Cookies";
        String search = "Cookies";
        String[] results;

        testdb.createRecipe(test1);
        testdb.createRecipe(test2);

        assertNotNull(testdb.getRecipe(test1));
        assertNotNull(testdb.getRecipe(test2));

        results = testdb.searchRecipeNames(search);
        assertEquals(results.length, 2);

        results = testdb.searchRecipeNames(search.toLowerCase());
        assertEquals(results.length, 2);

        results = testdb.searchRecipeNames(test1);
        assertEquals(results.length, 1);

        results = testdb.searchRecipeNames("g");
        assertEquals(results.length, 1);

        results = testdb.searchRecipeNames(null);
        assertEquals(results.length, 0);

        results = testdb.searchRecipeNames("Sludge");
        assertEquals(results.length, 0);
    }

    @Test
    public void testDelete(){
        String test1 = "Sugar Cookies";
        String test2 = "Chocolate Chip Cookies";
        String[] results;

        assertFalse(testdb.deleteRecipe(test1));

        testdb.createRecipe(test1);
        testdb.createRecipe(test2);

        assertTrue(testdb.deleteRecipe(test1));

        results = testdb.getRecipeNames();
        assertEquals(results.length, 1);
        assertEquals(results[0], test2);

        assertFalse(testdb.deleteRecipe(test1));
        assertFalse(testdb.deleteRecipe(null));

        assertTrue(testdb.deleteRecipe(test2));
        assertFalse(testdb.deleteRecipe(test2));

        results = testdb.getRecipeNames();
        assertEquals(results.length,0);
    }

    @Test
    public void testNameUpdate(){
        String name1 = "Cookie";
        String name2 = "Chocolate Chip Cookie";
        String name3 = "Sugar Cookie";

        testdb.createRecipe(name1);
        assertTrue(testdb.updateRecipeName(name1, name1));
        assertNotNull(testdb.getRecipe(name1));
        assertFalse(testdb.updateRecipeName(name1,null));
        assertTrue(testdb.updateRecipeName(name1,name2));

        testdb.createRecipe(name3);
        assertFalse(testdb.updateRecipeName(name3, name2)); // name already taken
        assertTrue(testdb.updateRecipeName(name3, name1)); // name should be free now
    }

    @Test
    public void testCommitChanges(){
        Recipe tester = new RecipeExample();
        Recipe result;

        assertFalse(testdb.commitChanges(tester));

        testdb.createRecipe(tester.getTitle());
        assertTrue(testdb.commitChanges(tester));

        result = testdb.getRecipe(tester.getTitle());
        assertEquals(result.ingredientCount(), tester.ingredientCount());

        testdb.deleteRecipe(tester.getTitle());
        assertFalse(testdb.commitChanges(tester));

        assertFalse(testdb.commitChanges(null));
    }

    @Test
    public void testCopier(){
        assertNull(testdb.duplicateRecipe("",""));
    }

}
