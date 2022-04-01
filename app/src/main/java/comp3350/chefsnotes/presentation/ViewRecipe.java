package comp3350.chefsnotes.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import comp3350.chefsnotes.R;
import comp3350.chefsnotes.application.Services;
import comp3350.chefsnotes.business.IRecipeFetcher;
import comp3350.chefsnotes.business.IRecipeManager;
import comp3350.chefsnotes.business.ITagHandler;
import comp3350.chefsnotes.business.RecipeFetcher;
import comp3350.chefsnotes.business.RecipeManager;
import comp3350.chefsnotes.business.TagHandler;
import comp3350.chefsnotes.objects.Recipe;


import android.widget.ArrayAdapter;
import android.widget.ToggleButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;

public class ViewRecipe extends AppCompatActivity {

    private final IRecipeFetcher recipeFetcher = new RecipeFetcher(Services.getRecipePersistence());
    private Recipe recipe;
    //private boolean valid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //valid = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        ImageButton editButton = findViewById(R.id.edit_button);
        ImageButton copyButton = findViewById(R.id.copy_button);

        editButton.setOnClickListener(this::editRecipe);
        copyButton.setOnClickListener(this::copyRecipe);

        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
        navView.setOnItemSelectedListener(this::navigation);

        String recipeName = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            recipeName = extras.getString("recipeKey");
            recipe = recipeFetcher.getRecipeByName(recipeName);
        }

        if (recipe != null) {
            fillViewer();
            populateTags(recipe);
        } else {
            errorScreen();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        String recipeName = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            recipeName = extras.getString("recipeKey");
            recipe = recipeFetcher.getRecipeByName(recipeName);
        } else {
            recipe = recipeFetcher.getRecentRecipe();
        }

        if (recipe != null) {
            fillViewer();
        } else {
            errorScreen();
        }
    }

    private void editRecipe(View view) {
        //perform action to populate recipe - must be added somewhere
        //if(valid) {
            Intent switchActivityIntent = new Intent(this, EditRecipe.class);
            switchActivityIntent.putExtra("title", recipe.getTitle());
            startActivity(switchActivityIntent);
        //}
    }

    private void copyRecipe(View view) {
        //perform action to populate recipe - must be added somewhere
        //if(valid) {
            IRecipeManager manager = new RecipeManager(Services.getRecipePersistence());
            String title;
            Intent switchActivityIntent = new Intent(this, EditRecipe.class);

            title = manager.copyRecipe(recipe, null).getTitle();
            switchActivityIntent.putExtra("title", title);
            startActivity(switchActivityIntent);
      //  }
    }

    private void fillViewer() {
        //valid = true;
        String[] directionsTemp = recipe.getDirectionStrings();
        String[] ingredients = recipe.getIngredientStrings();
        String title = recipe.getTitle();
        String time = directionsTemp[0];

        String[] directions = Arrays.copyOfRange(directionsTemp, 1, directionsTemp.length);

        ((TextView) findViewById(R.id.recipeName)).setText(title);

        //Ingredient List
        ArrayAdapter<String> ingAdapter = new ArrayAdapter<>(this,
                R.layout.list_style, ingredients);
        ListView ingView = findViewById(R.id.ingredientListView);
        ingView.setAdapter(ingAdapter);

        //Direction List
        ArrayAdapter<String> dirAdapter = new ArrayAdapter<>(this,
                R.layout.list_style, directions);
        ListView dirView = findViewById(R.id.directionListView);
        dirView.setAdapter(dirAdapter);

        ((TextView) findViewById(R.id.totalTimeView)).setText(time);
    }

    private void errorScreen() {
        //valid = false;
        String error = "Please select a new recipe";
        ((TextView) findViewById(R.id.recipeName)).setText(error);
       // Messages.oops(this,
       //         "This is not a valid recipe. Try making a new one or open one from the browser.");
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ViewRecipe.this, RecipeBrowser.class);
        Log.d("CDA", "onBackPressed Called");
        startActivity(i);
    }

    private boolean navigation(MenuItem item) {
        if (item.getItemId() == R.id.new_recipe_button) {
            Intent i = new Intent(ViewRecipe.this, EditRecipe.class);
            startActivity(i);
            return true;
        } else if (item.getItemId() == R.id.browse_recipe_button) {
            Intent i = new Intent(ViewRecipe.this, RecipeBrowser.class);
            startActivity(i);
            return true;
        } else if (item.getItemId() == R.id.current_recipe_button) {
            return super.onOptionsItemSelected(item);
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void populateTags(Recipe r){
        ITagHandler tagHandler = new TagHandler(Services.getTagPersistence(), Services.getRecipePersistence());

        Flow tags = findViewById(R.id.viewTags);
        ConstraintLayout parent = (ConstraintLayout) findViewById(R.id.tagContainerView);

        ArrayList<String> tagList = r.getTags();

        int [] idList = new int[tagList.size()];
        int i=0;

        for (String s : tagList) {

            ToggleButton b = new ToggleButton(this);
            b.setTextSize(11);
            b.setText(s);
            b.setTextOff(s);
            b.setTextOn(s);
            b.setMinHeight(20);
            b.setMinimumHeight(20);
            b.setMinWidth(50);
            b.setMinimumWidth(50);
            b.setPadding(10, 5, 10, 5);
            b.setAllCaps(false);

            b.setId(b.generateViewId());


            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.togglebutton_selector, null);

            ViewCompat.setBackground(b, drawable);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMarginStart(8);
            params.setMarginEnd(8);
            b.setLayoutParams(params);

            parent.addView(b);
            tags.addView(b);
            idList[i] = b.getId();
            i++;

        }
        tags.setReferencedIds(idList);
    }
}
