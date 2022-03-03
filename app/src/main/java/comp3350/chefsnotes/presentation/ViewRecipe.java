package comp3350.chefsnotes.presentation;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import comp3350.chefsnotes.R;
import comp3350.chefsnotes.objects.Direction;
import comp3350.chefsnotes.objects.Ingredient;
import comp3350.chefsnotes.objects.Recipe;
import android.widget.ArrayAdapter;

public class ViewRecipe extends AppCompatActivity {
    private Recipe recipe;
//    private Direction[] directionArray;
//    private Ingredient[] ingredientList;
//    private ArrayAdapter<Direction> directionAdapter;
//    private ArrayAdapter<Ingredient> ingredientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);
        makeRec();
        //this.rec = getRecipe();
        fill();

    }

    private void fill(){
        final ListView listView = (ListView)findViewById(R.id.IngredientListView);
        String title= "This is the recipe title!";
        ((TextView)findViewById(R.id.recipeName)).setText(recipe.getTitle());
        ((TextView)findViewById(R.id.recipeName)).setText(title);


    }

    private void makeRec(){
        recipe = new Recipe("Temporary Recipe");
        recipe.addIngredient("Ing1", 5, "unit");
        recipe.addDirection("Here's a direction!");
    }




}