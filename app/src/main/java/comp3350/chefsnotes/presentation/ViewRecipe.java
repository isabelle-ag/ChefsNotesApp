package comp3350.chefsnotes.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import comp3350.chefsnotes.R;
import comp3350.chefsnotes.application.Services;
import comp3350.chefsnotes.business.IRecipeFetcher;
import comp3350.chefsnotes.business.IRecipeManager;
import comp3350.chefsnotes.business.RecipeFetcher;
import comp3350.chefsnotes.business.RecipeManager;
import comp3350.chefsnotes.objects.Recipe;


import android.widget.ArrayAdapter;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;

public class ViewRecipe extends AppCompatActivity {

    private final IRecipeFetcher recipeFetcher = new RecipeFetcher(Services.getRecipePersistence());
    private Recipe recipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        }

        recipe = recipeFetcher.getRecipeByName(recipeName);

        if (recipe != null) {
            fillViewer();
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
        Intent switchActivityIntent = new Intent(this, EditRecipe.class);
        switchActivityIntent.putExtra("title", recipe.getTitle());
        startActivity(switchActivityIntent);
    }

    private void copyRecipe(View view) {
        //perform action to populate recipe - must be added somewhere
        IRecipeManager manager = new RecipeManager(Services.getRecipePersistence());
        String title;
        Intent switchActivityIntent = new Intent(this, EditRecipe.class);

        title = manager.copyRecipe(recipe, null).getTitle();
        switchActivityIntent.putExtra("title", title);
        startActivity(switchActivityIntent);
    }

    private void fillViewer() {
        String[] directionsTemp = recipe.getDirectionStrings();
        String[] ingredients = recipe.getIngredientStrings();
        String title = recipe.getTitle();
        String time = directionsTemp[0];

        String[] directions = Arrays.copyOfRange(directionsTemp, 1, directionsTemp.length);

        ((TextView) findViewById(R.id.recipeName)).setText(title);

        //Ingredient List
        ArrayAdapter<String> ingAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, ingredients);
        ListView ingView = findViewById(R.id.ingredientListView);
        ingView.setAdapter(ingAdapter);

        //Direction List
        ArrayAdapter<String> dirAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, directions);
        ListView dirView = findViewById(R.id.directionListView);
        dirView.setAdapter(dirAdapter);

        ((TextView) findViewById(R.id.totalTimeView)).setText(time);
    }

    private void errorScreen() {
        String error = "There are no recipes at the moment.";

        ((TextView) findViewById(R.id.recipeName)).setText(error);
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
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
