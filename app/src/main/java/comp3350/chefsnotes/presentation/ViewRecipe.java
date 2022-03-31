package comp3350.chefsnotes.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import comp3350.chefsnotes.R;
import comp3350.chefsnotes.application.Services;
import comp3350.chefsnotes.business.RecipeFetcher;
import comp3350.chefsnotes.objects.Recipe;


import android.widget.ArrayAdapter;

import java.util.Arrays;

public class ViewRecipe extends AppCompatActivity {

    private final RecipeFetcher recipeFetcher = new RecipeFetcher(Services.getRecipePersistence());
    private Recipe recipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        ImageButton editButton = findViewById(R.id.edit_button);
        ImageButton copyButton = findViewById(R.id.copy_button);

        editButton.setOnClickListener(this::editRecipe);
        copyButton.setOnClickListener(this::copyRecipe);

        String recipeName = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            recipeName = extras.getString("recipeKey");
        }

        recipe = recipeFetcher.getRecipeByName(recipeName);

        if(recipe != null) {
            fillViewer();
        }
        else{
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
        }
        else{
            recipe = recipeFetcher.getRecentRecipe();
        }

        if(recipe != null) {
            fillViewer();
        }
        else{
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
        String title;
        Intent switchActivityIntent = new Intent(this, EditRecipe.class);
        title = Services.getRecipePersistence().duplicateRecipe(recipe.getTitle(), null);
        switchActivityIntent.putExtra("title", title);
        startActivity(switchActivityIntent);
    }

    private void fillViewer(){
        String[] directionsTemp = recipe.getDirectionStrings();
        String[] ingredients = recipe.getIngredientStrings();
        String title = recipe.getTitle();
        String time = directionsTemp[0];

        String[] directions = Arrays.copyOfRange(directionsTemp, 1, directionsTemp.length);

        ((TextView)findViewById(R.id.recipeName)).setText(title);

        //Ingredient List
        ArrayAdapter<String> ingAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, ingredients);
        ListView ingView = (ListView) findViewById(R.id.ingredientListView);
        ingView.setAdapter(ingAdapter);

        //Direction List
        ArrayAdapter<String> dirAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, directions);
        ListView dirView = (ListView) findViewById(R.id.directionListView);
        dirView.setAdapter(dirAdapter);

        ((TextView)findViewById(R.id.totalTimeView)).setText(time);
    }

    private void errorScreen(){
        String error = "There are no recipes at the moment.";

        ((TextView)findViewById(R.id.recipeName)).setText(error);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ViewRecipe.this, RecipeBrowser.class);
        Log.d("CDA", "onBackPressed Called");
        startActivity(i);
    }




}
