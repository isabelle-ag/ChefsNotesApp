package comp3350.chefsnotes.iteration2features;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import comp3350.chefsnotes.R;
import comp3350.chefsnotes.business.IRecipeFetcher;
import comp3350.chefsnotes.business.IRecipeManager;
import comp3350.chefsnotes.business.ITagHandler;
import comp3350.chefsnotes.business.RecipeFetcher;
import comp3350.chefsnotes.business.RecipeManager;
import comp3350.chefsnotes.objects.Direction;
import comp3350.chefsnotes.objects.Ingredient;
import comp3350.chefsnotes.objects.Recipe;
import comp3350.chefsnotes.objects.RecipeExample;
import comp3350.chefsnotes.objects.RecipeExistenceException;
import comp3350.chefsnotes.persistence.DBMSTools;
import comp3350.chefsnotes.persistence.RecipePersistence;
import comp3350.chefsnotes.presentation.RecipeBrowser;
import comp3350.chefsnotes.utils.TestUtils;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EditRecipeTest {
    @Rule
    public ActivityTestRule<RecipeBrowser> activityRule = new ActivityTestRule<>(RecipeBrowser.class);

    private IRecipeManager manager;
    private IRecipeFetcher fetcher;
    private ITagHandler handler;
    private File tempDB;
    private final String testName = "Chocolate Chip Cookies";
    private Recipe example;

    //shamelessly copied from the sample project
    @Before
    public void setUp() throws IOException, RecipeExistenceException {
        example = new RecipeExample();
        this.tempDB = TestUtils.copyDB();
        final DBMSTools persistence = new RecipePersistence(this.tempDB.getAbsolutePath().replace(".script", ""));
        this.manager = new RecipeManager(persistence);
        this.fetcher = new RecipeFetcher(persistence);
        this.manager.saveButton(example.getTitle(), (ArrayList<Ingredient>) Arrays.asList(example.getIngredients()), (ArrayList<Direction>) Arrays.asList(example.getDirections()), true);
    }

    @Test
    public void test1()
    {
    }
}
