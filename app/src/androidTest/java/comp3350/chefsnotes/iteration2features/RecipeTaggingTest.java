package comp3350.chefsnotes.iteration2features;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
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
public class RecipeTaggingTest {
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

//  User story met: As a user, I should be able to select from previous tags I have used
    @Test
    public void testBuiltInTags()
    {
        onView(withId(R.id.new_recipe_button)).perform(click());
        onView(withId(R.id.recipeTitle)).perform(replaceText("ඞඞඞඞ"));
        onView(withText("African")).perform(click());
        onView(withId(R.id.save_button)).perform(click());
        onView(withText("African")).check(matches(isDisplayed()));
        onView(withText("ඞඞඞඞ")).check(matches(isDisplayed()));
    }

//  User story met: As a user, I should be able to add category tags to my Recipes
    @Test
    public void testCustomTags()
    {
        onView(withId(R.id.new_recipe_button)).perform(click());
        onView(withId(R.id.recipeTitle)).perform(replaceText("ඞඞඞඞ"));
        onView(withText("Create New Tag")).perform(click());
        //childAtPosition class provided by android studio to increase specificity for esspresso tests
        onView(allOf(childAtPosition(allOf(withId(androidx.appcompat.R.id.custom), childAtPosition(withId(androidx.appcompat.R.id.customPanel), 0)), 0), isDisplayed())).perform(replaceText("3350"), closeSoftKeyboard());
        onView(withText("OK")).perform(click());
        onView(withText("3350")).perform(click());
        onView(withId(R.id.save_button)).perform(click());
        onView(withText("3350")).check(matches(isDisplayed()));

        onView(withId(R.id.edit_button)).perform(click());
        onView(withText("3350")).perform(click());
        onView(withId(R.id.save_button)).perform(click());
        onView(withId(R.id.edit_button)).perform(click());
        onView(withText("Delete Existing Tag")).perform(click());
        onView(allOf(childAtPosition(allOf(withId(androidx.appcompat.R.id.custom), childAtPosition(withId(androidx.appcompat.R.id.customPanel), 0)), 0), isDisplayed())).perform(replaceText("3350"), closeSoftKeyboard());
        onView(withText("Delete")).perform(click());
        onView(withText("3350")).check(doesNotExist());

    }

    //provided by android studio to use more descriptive references to views, necessary for refering to popup boxes
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
