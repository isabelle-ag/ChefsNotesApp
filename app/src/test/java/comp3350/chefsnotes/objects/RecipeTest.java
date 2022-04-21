package comp3350.chefsnotes.objects;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import comp3350.chefsnotes.business.Units;

public class RecipeTest {
    private Recipe example;
    private final String testName = "#3350TEST";

    @Before
    public void initialize(){
        example = new RecipeExample();
    }

    @Test
    public void testGettersAndSetters(){
        String nameChange = "Cookies";

        assertEquals(example.ingredientCount(),11);
        assertEquals(example.directionCount(), 4);
        assertEquals(example.getTitle(), testName);

    }

    @Test
    public void testAdders(){
        int prevCount;

        prevCount = example.ingredientCount();
        assertTrue(example.addIngredient("Peanut Butter",2, Units.TBSP));
        assertEquals(example.ingredientCount(),prevCount+1);

        prevCount = example.ingredientCount();
        assertTrue(example.addIngredient("Sprinkles",3,4, Units.CUP));
        assertEquals(example.ingredientCount(), prevCount+1);

        prevCount = example.directionCount(); // count is highest index+1
        assertEquals(example.addDirection(new Direction("Set to cool for 20 minutes",20)),prevCount); // returns index

        prevCount = example.directionCount();
        assertEquals(example.addDirection(new Direction("Serve and enjoy!","Final step")),prevCount);
    }

    @Test
    public void testDeletion(){
        String removed = "Flour";
        int prevCount;

        prevCount = example.directionCount();
        assertTrue(example.deleteDirection(prevCount-1));
        assertEquals(example.directionCount(),prevCount-1);

        prevCount = example.ingredientCount();
        assertTrue(example.deleteIngredient(removed));
        assertEquals(example.ingredientCount(),prevCount-1);
        assertNull(example.getIngredient(removed));

        prevCount = example.ingredientCount();
        assertTrue(example.deleteIngredient(prevCount-1));
        assertEquals(example.ingredientCount(),prevCount-1);

    }

    @Test
    public void testClone() {
        Recipe exampleClone = example.clone();
        assertEquals(example , exampleClone);
        assertFalse(example == exampleClone);
    }

    @Test
    public void testTags() {
        example.addTag("foo");
        assertTrue(example.getTags().contains("foo"));
        example.removeTag("foo");
        assertFalse(example.getTags().contains("foo"));
    }

}


