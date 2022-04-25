package comp3350.chefsnotes.iteration2features;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.anything;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import comp3350.chefsnotes.R;
import comp3350.chefsnotes.application.Services;
import comp3350.chefsnotes.business.IRecipeFetcher;
import comp3350.chefsnotes.business.IRecipeManager;
import comp3350.chefsnotes.business.ITagHandler;
import comp3350.chefsnotes.objects.Recipe;
import comp3350.chefsnotes.objects.RecipeExample;
import comp3350.chefsnotes.persistence.DBMSTools;
import comp3350.chefsnotes.presentation.MainActivity;
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeBrowserTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    private IRecipeManager manager;
    private IRecipeFetcher fetcher;
    private ITagHandler handler;
    private File tempDB;
    private final String testName = "Chocolate Chip Cookies";
    private Recipe example;
    DBMSTools recipes;

    //shamelessly copied from the sample project
    @Before
    public void setUp(){
        example = new RecipeExample();
        recipes = Services.getRecipePersistence();
        String[] recs = recipes.getRecipeNames();

        for (String r: recs) {
            System.out.println("RECIPE: " + r);
            if (r.equals("#3350TEST")) {
                recipes.deleteRecipe(r);
            }
        }

        boolean insert = recipes.createRecipe(example.getTitle());
        if(insert){
            recipes.commitChanges(example);
        }
    }

    @AfterClass
    public static void cleanUp()//ensure test recipe doesn't stick around
    {
        DBMSTools recipes = Services.getRecipePersistence();
        String[] recs = recipes.getRecipeNames();

        for (String r: recs) {
            System.out.println("RECIPE: " + r);
            if (r.equals("#3350TEST") || r.equals("ඞඞඞඞ")) {
                recipes.deleteRecipe(r);
            }
        }
    }

//  User story met: As a user, I should be able to look through a list of all my recipes
    @Test
    public void testBrowserList()
    {
        int i = 0;
        onView(withId(R.id.browse_recipe_button)).perform(click());

        for(Recipe rec: recipes.getAllRecipes())
        {
            onData(anything()).inAdapterView(withId(R.id.results)).atPosition(i++).check(matches(withText(rec.getTitle())));
        }
    }

//  User story met: As a user, I should be able to find a recipe I have, by name
    @Test
    public void testSearch()
    {
        onView(withId(R.id.browse_recipe_button)).perform(click());
        onView(withId(R.id.searchRecipeName)).perform(typeText("#3350TEST"));
        onData(anything()).inAdapterView(withId(R.id.results)).atPosition(0).check(matches(withText("#3350TEST")));
    }

}
