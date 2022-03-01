package comp3350.chefsnotes.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import comp3350.chefsnotes.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void makeRecipe(View view)
    {
        setContentView(R.layout.recipe_editor);
    }

    public void addInstruction(View view)
    {
        LinearLayout instructionContainer = (LinearLayout) findViewById(R.id.InstructionContainer);
        View child = getLayoutInflater().inflate(R.layout.instruction_field, null);
        instructionContainer.addView(child);
    }

    public void addIngredient(View view)
    {
        LinearLayout ingredientContainer = (LinearLayout) findViewById(R.id.IngredientContainer);
        View child = getLayoutInflater().inflate(R.layout.ingredient_field, null);
        ingredientContainer.addView(child);
    }
}
