package comp3350.chefsnotes;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.chefsnotes.iteration1features.RecipeCreationTest;
import comp3350.chefsnotes.iteration1features.RecipeViewerTest;
import comp3350.chefsnotes.iteration2features.EditRecipeTest;
import comp3350.chefsnotes.iteration2features.RecipeBrowserTest;
import comp3350.chefsnotes.iteration2features.RecipeTaggingTest;
import comp3350.chefsnotes.iteration2features.TagFilteringTest;
import comp3350.chefsnotes.iteration3features.IngredientBrowsingTest;
import comp3350.chefsnotes.iteration3features.PhotosAndNotesTest;
import comp3350.chefsnotes.iteration3features.RecipeExportingTest;

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
        TagFilteringTest.class
})
public class AllAcceptanceTests {
}
