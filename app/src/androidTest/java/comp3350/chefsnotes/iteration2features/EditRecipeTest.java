package comp3350.chefsnotes.iteration2features;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.anything;

import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.CoreMatchers;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import comp3350.chefsnotes.R;
import comp3350.chefsnotes.application.Services;
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
import comp3350.chefsnotes.presentation.MainActivity;
import comp3350.chefsnotes.presentation.RecipeBrowser;
import comp3350.chefsnotes.presentation.ViewRecipe;
import comp3350.chefsnotes.utils.TestUtils;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EditRecipeTest {
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

    @Test
    public void testAlterIngs()
    {
        onView(withId(R.id.browse_recipe_button)).perform(click());
        onView(withId(R.id.searchRecipeName)).perform(typeText("#3350TEST"));
        onData(anything()).atPosition(0).perform(click());
        onView(withText(example.getIngredientStrings()[0])).check(matches(isDisplayed()));
        //delete ingredient and save
        onView(withId(R.id.edit_button)).perform(click());
        onView(allOf(withId(R.id.IngredientDeleteButton), hasSibling(withText("Butter")))).perform(click());
        onView(withId(R.id.save_button)).perform(click());
        //pressBack();
        onView(withText(example.getIngredientStrings()[0])).check(doesNotExist());
    }

    @Test
    public void testAlterTitle()
    {
        onView(withId(R.id.browse_recipe_button)).perform(click());
        onView(withId(R.id.searchRecipeName)).perform(typeText("#3350TEST"));
        onData(anything()).atPosition(0).perform(click());
        onView(withText("#3350TEST")).check(matches(isDisplayed()));
        //delete ingredient and save
        onView(withId(R.id.edit_button)).perform(click());
        onView(withId(R.id.recipeTitle)).perform(replaceText("ඞඞඞඞ"));
        onView(withId(R.id.save_button)).perform(click());
        //pressBack();
        onView(withText("#3350TEST")).check(doesNotExist());
        onView(withText("ඞඞඞඞ")).check(matches(isDisplayed()));
    }

    @Test
    public void testDelete()
    {
        onView(withId(R.id.browse_recipe_button)).perform(click());
        onView(withId(R.id.searchRecipeName)).perform(typeText("#3350TEST"));
        onData(anything()).atPosition(0).perform(click());
        //delete
        onView(withId(R.id.edit_button)).perform(click());
        onView(withId(R.id.delRecipe)).perform(click());
        //pressBack();
        onView(withId(R.id.searchRecipeName)).perform(typeText("#3350TEST"));
        onView(withText("#3350TEST")).check(doesNotExist());
    }


}
