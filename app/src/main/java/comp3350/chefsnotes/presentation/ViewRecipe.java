package comp3350.chefsnotes.presentation;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;


import comp3350.chefsnotes.R;
import comp3350.chefsnotes.business.RecipeFetcher;
import comp3350.chefsnotes.objects.Recipe;
import comp3350.chefsnotes.persistence.FakeDBMS;

import android.widget.ArrayAdapter;

import java.util.Arrays;

public class ViewRecipe extends AppCompatActivity {
    private RecipeFetcher recipeFetcher = new RecipeFetcher(new FakeDBMS());
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        recipe = recipeFetcher.getRecentRecipe("Use Test Recipe");
        if(recipe != null) {
            fillViewer();
        }
        else{
            errorScreen();
        }
    }

    private void fillViewer(){
        String[] directionsTemp = recipe.getDirectionStrings();
        String[] ingredients = recipe.getIngredientStrings();
        String title = recipe.getTitle();
        String time = directionsTemp[0];

        String[] directions = Arrays.copyOfRange(directionsTemp, 1, directionsTemp.length);

        ((TextView)findViewById(R.id.recipeName)).setText(title);

        //Ingredient List
        ArrayAdapter<String> ingAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, ingredients);
        ListView ingView = (ListView) findViewById(R.id.ingredientListView);
        ingView.setAdapter(ingAdapter);

        //Direction List
        ArrayAdapter<String> dirAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, directions);
        ListView dirView = (ListView) findViewById(R.id.directionListView);
        dirView.setAdapter(dirAdapter);

        ((TextView)findViewById(R.id.totalTimeView)).setText(time);


    }

    private void errorScreen(){
        String error = "There are no recipes at the moment.";

        ((TextView)findViewById(R.id.recipeName)).setText(error);
    }




}