package comp3350.chefsnotes.iteration2features;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;

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
import comp3350.chefsnotes.presentation.ViewRecipe;
import comp3350.chefsnotes.utils.TestUtils;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EditRecipeTest {
    @Rule
    public ActivityTestRule<ViewRecipe> activityRule = new ActivityTestRule<>(ViewRecipe.class);

    private IRecipeManager manager;
    private IRecipeFetcher fetcher;
    private ITagHandler handler;
    private File tempDB;
    private final String testName = "Chocolate Chip Cookies";
    private Recipe example;

    //shamelessly copied from the sample project
    @Before
    public void setUp() throws IOException, RecipeExistenceException {
        ArrayList<Ingredient> ings = new ArrayList<Ingredient>();
        ArrayList<Direction> dirs = new ArrayList<Direction>();
        example = new RecipeExample();

        ings.addAll(Arrays.asList(example.getIngredients()));
        dirs.addAll(Arrays.asList(example.getDirections()));


        this.tempDB = TestUtils.copyDB();
        final DBMSTools persistence = new RecipePersistence(this.tempDB.getAbsolutePath().replace(".script", ""));
        this.manager = new RecipeManager(persistence);
        this.fetcher = new RecipeFetcher(persistence);
        try
        {
            this.manager.saveButton(example.getTitle(), ings, dirs, true);
        }
        catch (RecipeExistenceException e)
        {
        }

        //launch recipe viewer with testrecipe
        Intent myIntent = new Intent();
        myIntent.putExtra("title", testName);
        activityRule.launchActivity(myIntent);
    }

    @Test
    public void test1()
    {
    }
}
