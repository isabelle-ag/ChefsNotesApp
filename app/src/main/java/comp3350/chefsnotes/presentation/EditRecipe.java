package comp3350.chefsnotes.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;

import comp3350.chefsnotes.R;
import comp3350.chefsnotes.objects.Direction;
import comp3350.chefsnotes.objects.Ingredient;

public class EditRecipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_editor);

        ImageButton saveButton = findViewById(R.id.save_button);
        View addIngredientButton = findViewById(R.id.AddIngredientButton);
        View addInstructionButton = findViewById(R.id.AddDirectionButton);
        View deleteIngredientButton = findViewById(R.id.IngredientDeleteButton);

        saveButton.setOnClickListener(v -> {
            String title = getTitle(v);
            ArrayList<Ingredient> ingredients = getIngredients(v);
            ArrayList<Direction> directions = getDirections(v);

            if(ingredients != null && directions != null)
            {
                System.out.println("Saving " + title + "...");
            }
            else
            {
                System.out.println("Abort save, null field found.");
            }
        });

        addInstructionButton.setOnClickListener(this::addDirection);
        addIngredientButton.setOnClickListener(this::addIngredient);
        deleteIngredientButton.setOnClickListener(this::removeIngredient);

    }

    public void addDirection(View view)
    {
        LinearLayout instructionContainer = findViewById(R.id.InstructionContainer);
        View child = getLayoutInflater().inflate(R.layout.instruction_field, null);
        instructionContainer.addView(child);
    }

    public void addIngredient(View view)
    {
        LinearLayout ingredientContainer = findViewById(R.id.IngredientContainer);
        View child = getLayoutInflater().inflate(R.layout.ingredient_field, null);
        ingredientContainer.addView(child);

        View deleteIngredientButton = child.findViewById(R.id.IngredientDeleteButton);
        deleteIngredientButton.setOnClickListener(this::removeIngredient);
    }

    private String getTitle(View view)
    {
        return ((EditText) findViewById(R.id.recipeTitle)).getText().toString();
    }

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
                    ((Spinner)currRow.findViewById(R.id.UnitList))
                            .getSelectedItem().toString();

            if(!ingredientName.equals("") && !ingredientCount.equals("") && !ingredientUnit.equals(""))
            {
                current = new Ingredient(ingredientName, Double.parseDouble(ingredientCount), ingredientUnit);
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

    private ArrayList<Direction> getDirections(View view)
    {
        ArrayList<Direction> directions = new ArrayList<>();
        Direction current;
        LinearLayout ingredients = findViewById(R.id.InstructionContainer);
        View currRow;

        for(int i = 0; i < ingredients.getChildCount(); i++)
        {
            currRow = ingredients.getChildAt(i);

            String instructionName =
                    ((EditText)currRow.findViewById(R.id.DirectionName))
                            .getText().toString();

            String instructions =
                    ((EditText)currRow.findViewById(R.id.Directions))
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
    
    public void removeDirection(View view)
    {
        LinearLayout ingredientContainer = (LinearLayout) findViewById(R.id.InstructionContainer);
        ingredientContainer.removeView((View) view.getParent().getParent());
    }

    public void removeIngredient(View view)
    {
        LinearLayout ingredientContainer = (LinearLayout) findViewById(R.id.IngredientContainer);
        ingredientContainer.removeView((View) view.getParent().getParent());
    }
}
