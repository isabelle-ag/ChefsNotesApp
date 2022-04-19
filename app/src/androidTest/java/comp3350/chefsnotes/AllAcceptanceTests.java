package comp3350.chefsnotes;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        EditRecipeTest.class,
        IngredientBrowsingTest.class,
        PhotosAndNotesTest.class,
        RecipeBrowserTest.class,
        RecipeCreationTest.class,
        RecipeExportingTest.class,
        RecipeTaggingTest.class,
        RecipeViewerTest.class,
        ServingSizeTest.class,
        TagFilteringTest.class
})
public class AllAcceptanceTests {
}
