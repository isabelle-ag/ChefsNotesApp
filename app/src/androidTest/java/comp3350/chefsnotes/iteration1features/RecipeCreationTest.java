package comp3350.chefsnotes.iteration1features;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

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
public class RecipeCreationTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    private IRecipeManager manager;
    private IRecipeFetcher fetcher;
    private ITagHandler handler;
    private File tempDB;
    private final String testName = "Chocolate Chip Cookies";
    private Recipe example;

    //shamelessly copied from the sample project
    @Before
    public void setUp(){
        example = new RecipeExample();
        DBMSTools recipes = Services.getRecipePersistence();
        String[] recs = recipes.getRecipeNames();

        for (String r: recs) {
            System.out.println("RECIPE: " + r);
            if (r.equals("#3350TEST") || r.equals("ඞඞඞඞ")) {
                recipes.deleteRecipe(r);
            }
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

    @Test
    public void testMakeRecWIng()
    {
        onView(withId(R.id.new_recipe_button)).perform(click());

        onView(withId(R.id.recipeTitle)).perform(replaceText("ඞඞඞඞ"));

        onView(withId(R.id.IngredientName)).perform(typeText("Butter"));

        onView(withId(R.id.IngredientAmount)).perform(typeText("8"));

        onView(withId(R.id.unitList)).perform(click());

        onData(anything()).atPosition(2).perform(click());

        onView(withId(R.id.save_button)).perform(click());

        onView(allOf(withId(R.id.recipeName), withText("ඞඞඞඞ"), isDisplayed())).check(matches(withText("ඞඞඞඞ")));

        onView(allOf(withText("8 Ls Butter"), withParent(withId(R.id.ingredientListView)), isDisplayed())).check(matches(withText("8 Ls Butter")));
    }

    @Test
    public void testMakeRecWDir() {
        onView(allOf(withId(R.id.CreateRecipeButton), withText("Create Recipe"), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.recipeTitle))).perform(scrollTo(), replaceText("#3350TEST"), ViewActions.closeSoftKeyboard());

        onView(allOf(withId(R.id.DirectionName), isDisplayed())).perform(replaceText("Whistle"), ViewActions.closeSoftKeyboard());

        onView(allOf(withId(R.id.TimeEstimate), isDisplayed())).perform(replaceText("3"), ViewActions.closeSoftKeyboard());

        onView(allOf(withId(R.id.InstructionBox),isDisplayed())).perform(replaceText("3 Whistle"), ViewActions.closeSoftKeyboard());

        onView(allOf(withId(R.id.save_button), withContentDescription("Save Recipe"), isDisplayed())).perform(click());

        onView(allOf(withId(android.R.id.text1), withText("Step 1\t\t\t\tWhistle\n3 Whistle\nTime: 3 minutes"),withParent(withId(R.id.directionListView)),isDisplayed())).check(matches(withText("Step 1\t\t\t\tWhistle\n3 Whistle\nTime: 3 minutes")));

        onView(allOf(withId(R.id.totalTimeView), withText("3 minutes"), isDisplayed())).check(matches(withText("3 minutes")));

        onView(allOf(withId(R.id.recipeName), withText("#3350TEST"), isDisplayed())).check(matches(withText("#3350TEST")));
    }
}
