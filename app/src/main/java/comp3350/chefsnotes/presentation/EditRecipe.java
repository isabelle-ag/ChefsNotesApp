package comp3350.chefsnotes.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;

import comp3350.chefsnotes.R;
import comp3350.chefsnotes.application.Services;
import comp3350.chefsnotes.business.RecipeFetcher;
import comp3350.chefsnotes.business.RecipeManager;
import comp3350.chefsnotes.business.Units;
import comp3350.chefsnotes.objects.Direction;
import comp3350.chefsnotes.objects.Ingredient;
import comp3350.chefsnotes.objects.Recipe;
import comp3350.chefsnotes.objects.RecipeExistenceException;

public class EditRecipe extends AppCompatActivity {
    private RecipeFetcher recipeFetcher = new RecipeFetcher(Services.getRecipePersistence());//refactor to use services natively
    private RecipeManager recipeManager = new RecipeManager(Services.getRecipePersistence());//refactor to use services natively

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_editor);

        ImageButton saveButton = findViewById(R.id.save_button);
        View addIngredientButton = findViewById(R.id.AddIngredientButton);
        View addInstructionButton = findViewById(R.id.AddDirectionButton);
        View deleteIngredientButton = findViewById(R.id.IngredientDeleteButton);
        View deleteDirectionButton = findViewById(R.id.DirectionDeleteButton);

        //set dropdown menu to array being used - can be used in the future for filtering units by metric, imperial, etc.
        Spinner ingDrop = findViewById(R.id.unitList);
        ArrayAdapter<String> units = new ArrayAdapter<String>(EditRecipe.this, android.R.layout.simple_spinner_item, Units.unitList());
        units.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ingDrop.setAdapter(units);

        Intent thisIntent = getIntent();

        if(thisIntent.getStringExtra("title") != null)//if a new recipe is being created (blank), Title = null
        {
            populateRecipe(thisIntent.getStringExtra("title"), units);
        }

        saveButton.setOnClickListener(v -> {
            String title = getTitle(v);
            ArrayList<Ingredient> ingredients = getIngredients(v);
            ArrayList<Direction> directions = getDirections(v);

            if(ingredients != null && directions != null)
            {
                try {
                    System.out.println("Saving " + title + "...");
                    recipeManager.saveButton(title, ingredients, directions);
                }
                catch(RecipeExistenceException e) {
                    System.out.println("Saving failed!");
                    System.out.println(e);
                }
            }
            else
            {
                System.out.println("Abort save, null field found.");
            }
        });

        addInstructionButton.setOnClickListener(this::addDirection);
        addIngredientButton.setOnClickListener(this::addIngredient);
        deleteIngredientButton.setOnClickListener(this::removeIngredient);
        deleteDirectionButton.setOnClickListener(this::removeDirection);

    }

    //put retrieval methods in separate class?
    private String getTitle(View view)
    {
        return ((EditText) findViewById(R.id.recipeTitle)).getText().toString();
    }

    //put retrieval methods in separate class?
    private ArrayList<Ingredient> getIngredients(View view)
    {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        Ingredient current;
        LinearLayout container = findViewById(R.id.IngredientContainer);
        View currRow;

        for(int i = 0; i < container.getChildCount(); i++)
        {
            currRow = container.getChildAt(i);

            String ingredientCount =
                    ((EditText)currRow.findViewById(R.id.IngredientAmount))
                            .getText().toString();

            String ingredientName =
                    ((EditText)currRow.findViewById(R.id.IngredientName))
                            .getText().toString();

            String ingredientUnit =
                    ((Spinner)currRow.findViewById(R.id.unitList))
                            .getSelectedItem().toString();

            if(!ingredientName.equals("") && !ingredientCount.equals("") && !ingredientUnit.equals(""))
            {
                if(ingredientCount.contains("/") || ingredientCount.contains("\\")) {
                    if(ingredientCount.contains("\\")) {
                        ingredientCount.replace("\\", "/");
                    }
                    int numer = Integer.parseInt(ingredientCount.substring(0, ingredientCount.indexOf("/")));
                    int denom = Integer.parseInt(ingredientCount.substring(ingredientCount.indexOf("/")+1, ingredientCount.length()));

                    current = new Ingredient(ingredientName, numer, denom, ingredientUnit);
                }
                else {
                    current = new Ingredient(ingredientName, Double.parseDouble(ingredientCount), ingredientUnit);
                }
                ingredients.add(current);
            }
            else
            {
                // Create some kind of UI error here for empty field
                System.out.println("Cannot Save ingredient! (Empty Field)");
                return null;
            }
        }

        return ingredients;
    }

    //put retrieval methods in separate class?
    private ArrayList<Direction> getDirections(View view)
    {
        ArrayList<Direction> directions = new ArrayList<>();
        Direction current;
        LinearLayout ingredients = findViewById(R.id.DirectionContainer);
        View currRow;

        for(int i = 0; i < ingredients.getChildCount(); i++)
        {
            currRow = ingredients.getChildAt(i);

            String instructionName =
                    ((EditText)currRow.findViewById(R.id.DirectionName))
                            .getText().toString();

            String instructions =
                    ((EditText)currRow.findViewById(R.id.textbox))
                            .getText().toString();

            String timeEstimate =
                    ((EditText)currRow.findViewById(R.id.TimeEstimate))
                            .getText().toString();

            if(!instructions.equals(""))
            {
                if(timeEstimate.equals(""))
                {
                    timeEstimate = "0";
                }
                if(instructionName.equals("Name")) {
                    instructionName = "";
                }
                current = new Direction(instructions, instructionName, Integer.parseInt(timeEstimate));
                directions.add(current);
            }
            else
            {
                // Create some kind of UI warning about empty field
                System.out.println("Cannot Save instruction!");
                return null;
            }
        }

        return directions;
    }

    public void populateRecipe(String title, ArrayAdapter<String> units)//cannot test until ViewRecipe uses real recipes, or sample recipe is stored in db
    {
        EditText name;
        EditText amount;
        EditText time;
        Spinner unitList;
        //get recipe from db
        //for each ingredient, id = ingredient.addIngredient(), findViewById(id).addText
        EditText recipeTitle = (EditText) findViewById(R.id.recipeTitle);
        recipeTitle.setText(title);
        Recipe populator = recipeFetcher.getRecipeByName(title);//use to populate fields
        ViewGroup curIng = findViewById(R.id.Ingredient);
        ViewGroup curDir = findViewById(R.id.Direction);
        for(Ingredient ing:populator.getIngredients())
        {
            name = (EditText) curIng.findViewById(R.id.IngredientName);
            name.setText(ing.getName());
            amount = (EditText) curIng.findViewById(R.id.IngredientAmount);
            amount.setText(ing.getAmt().getAmtStr());
            //figure out how to get the unit used by the current amount, and compare it to spinner values.
            unitList = (Spinner) curIng.findViewById(R.id.unitList);
            unitList.setSelection(units.getPosition(ing.getAmt().getUnit()));
            curIng = findViewById(this.addIngredient(findViewById(R.id.IngredientContainer)));
            Log.d("id", "" + curIng.getId());
        }
        for(Direction dir:populator.getDirections())
        {
            name = (EditText) curDir.findViewById(R.id.DirectionName);
            name.setText(dir.getName());
            time = (EditText) curDir.findViewById(R.id.TimeEstimate);
            time.setText(dir.getTime() + "");//Java is dumb
            EditText contents = (EditText) curDir.findViewById(R.id.textbox);
            contents.setText(dir.getText());
            curDir = findViewById(this.addDirection(findViewById(R.id.DirectionContainer)));
        }
    }

    public int addDirection(View view)
    {
        LinearLayout instructionContainer = findViewById(R.id.DirectionContainer);
        View child = getLayoutInflater().inflate(R.layout.instruction_field, null);
        instructionContainer.addView(child);
        child.setId(1000000 + child.getId() + instructionContainer.getChildCount());

        View deleteDirectionButton = child.findViewById(R.id.DirectionDeleteButton);
        deleteDirectionButton.setOnClickListener(this::removeDirection);

        return child.getId();
    }

    public int addIngredient(View view)
    {
        LinearLayout ingredientContainer = findViewById(R.id.IngredientContainer);
        View child = getLayoutInflater().inflate(R.layout.ingredient_field, null);
        ingredientContainer.addView(child);
        child.setId(1000000 + child.getId() + ingredientContainer.getChildCount());//set a unique id for the new child because Android Studio is too dumb to.

        View deleteIngredientButton = child.findViewById(R.id.IngredientDeleteButton);
        deleteIngredientButton.setOnClickListener(this::removeIngredient);

        Spinner ingDrop = child.findViewById(R.id.unitList);
        ArrayAdapter<String> units = new ArrayAdapter<String>(EditRecipe.this, android.R.layout.simple_spinner_item, Units.unitList());
        units.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ingDrop.setAdapter(units);

        //return id for population?
        return child.getId();
    }


    public void removeDirection(View view)
    {
        LinearLayout ingredientContainer = (LinearLayout) findViewById(R.id.DirectionContainer);
        ingredientContainer.removeView((View) view.getParent().getParent());
    }

    public void removeIngredient(View view)
    {
        LinearLayout ingredientContainer = (LinearLayout) findViewById(R.id.IngredientContainer);
        ingredientContainer.removeView((View) view.getParent().getParent());
    }
}
