package comp3350.chefsnotes.objects;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import comp3350.chefsnotes.business.Units;

public class RecipeTest {
    private Recipe example;
    private final String testName = "Chocolate Chip Cookies";

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
        assertTrue(example.getTags().contains("Foo"));
        example.removeTag("foo");
        assertFalse(example.getTags().contains("Foo"));
    }

    @Test
    public void testStringExport() {
        assertEquals("Chocolate Chip Cookies\n" +
                "\n" +
                "Ingredients:\n" +
                "1 cup Butter\n" +
                "1 cup White Sugar\n" +
                "1 cup Brown Sugar\n" +
                "2 Egg\n" +
                "2 tsps Vanilla Extract\n" +
                "1 tsp Baking Soda\n" +
                "2 tsps Hot Water\n" +
                "1/2 tsp Salt\n" +
                "3 cups Flour\n" +
                "2 cups Semisweet Chocolate Chips\n" +
                "1 cup Chopped Walnuts\n" +
                "\n" +
                "Directions:\n" +
                "Preheat: Preheat oven to 350 degrees F (175 degrees C). - 15 min\n" +
                "Mix Batter: Cream together the butter, white sugar, and brown sugar until smooth. Beat in the eggs one at a time, then stir in the vanilla.\n" +
                "Add solids: Dissolve baking soda in hot water. Add to batter along with salt. Stir in flour, chocolate chips, and nuts. Drop by large spoonfuls onto ungreased pans.\n" +
                "Bake cookies!: Bake for about 10 minutes in the preheated oven, or until edges are nicely browned. - 10 min\n\n", example.stringExport());
    }

}


