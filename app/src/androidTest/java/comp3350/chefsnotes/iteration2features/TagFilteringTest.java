package comp3350.chefsnotes.iteration2features;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

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
public class TagFilteringTest {
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
            if (r.equals("#3350TEST") || r.equals("ඞඞඞඞ") ) {
                recipes.deleteRecipe(r);
            }
        }
        onView(withId(R.id.new_recipe_button)).perform(click());

        onView(withId(R.id.recipeTitle)).perform(replaceText("ඞඞඞඞ"));
        onView(withText("African")).perform(click());//change to custom tag?
        onView(withId(R.id.save_button)).perform(click());
        onView(withId(R.id.browse_recipe_button)).perform(click());
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

    @Test
    public void testTagFilterAppear()
    {
        onView(withText("African")).perform(click());//change to custom tag?
        onData(anything()).inAdapterView(withId(R.id.results)).check(matches(withText("ඞඞඞඞ")));
    }

    @Test
    public void testTagFilterHide()
    {
        onView(withText("Asian")).perform(click());//change to custom tag?
        onView(withText("ඞඞඞඞ")).check(doesNotExist());
    }
}
