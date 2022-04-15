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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import comp3350.chefsnotes.R;
import comp3350.chefsnotes.application.Services;
import comp3350.chefsnotes.business.IRecipeFetcher;
import comp3350.chefsnotes.business.IRecipeManager;
import comp3350.chefsnotes.business.ITagHandler;
import comp3350.chefsnotes.business.RecipeFetcher;
import comp3350.chefsnotes.business.RecipeManager;
import comp3350.chefsnotes.business.TagHandler;
import comp3350.chefsnotes.business.Units;
import comp3350.chefsnotes.objects.Direction;
import comp3350.chefsnotes.objects.Ingredient;
import comp3350.chefsnotes.objects.Recipe;
import comp3350.chefsnotes.objects.RecipeExistenceException;

public class EditRecipe extends AppCompatActivity implements NoticeDialogFragment.NoticeDialogListener {
    private final IRecipeFetcher recipeFetcher = new RecipeFetcher(Services.getRecipePersistence());//refactor to use services natively
    private final IRecipeManager recipeManager = new RecipeManager(Services.getRecipePersistence());//refactor to use services natively
    private final ITagHandler tagHandler = new TagHandler(Services.getTagPersistence(), Services.getRecipePersistence());
    private ArrayList<String> tags;
    private ArrayList<String> oldTags;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_editor);
        tags = new ArrayList<>();
        oldTags = new ArrayList<>();

        ImageView saveButton = findViewById(R.id.save_button);
        View addIngredientButton = findViewById(R.id.AddIngredientButton);
        View addInstructionButton = findViewById(R.id.AddDirectionButton);
        View deleteIngredientButton = findViewById(R.id.IngredientDeleteButton);
        View deleteDirectionButton = findViewById(R.id.DirectionDeleteButton);
        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
        navView.setOnItemSelectedListener(this::navigation);
        ImageButton delRecipeButton = findViewById(R.id.delRecipe);

        //set dropdown menu to array being used - can be used in the future for filtering units by metric, imperial, etc.
        Spinner ingDrop = findViewById(R.id.unitList);
        ArrayAdapter<String> units = new ArrayAdapter<String>(EditRecipe.this, android.R.layout.simple_spinner_item, Units.unitList());
        units.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ingDrop.setAdapter(units);

        Intent thisIntent = getIntent();

        if(thisIntent.getStringExtra("title") != null)//if a new recipe is being created (blank), Title = null
        {
            populateRecipe(thisIntent.getStringExtra("title"), units);
            recipe = recipeFetcher.getRecipeByName(thisIntent.getStringExtra("title"));
        }

        populateTags();

        saveButton.setOnClickListener(this::save);
        addInstructionButton.setOnClickListener(this::addDirection);
        addIngredientButton.setOnClickListener(this::addIngredient);
        deleteIngredientButton.setOnClickListener(this::removeIngredient);
        deleteDirectionButton.setOnClickListener(this::removeDirection);
        delRecipeButton.setOnClickListener(this::deleteRecipe);

    }

    //put retrieval methods in separate class?
    private String getTitle(View view)
    {
        return ((EditText) findViewById(R.id.recipeTitle)).getText().toString();
    }

    private void save(View v){
        String title = getTitle(v);
        Intent thisIntent = getIntent();
        ArrayList<Ingredient> ingredients = getIngredients(v);
        ArrayList<Direction> directions = getDirections(v);
        Log.i("You are attempting to save!", "title");
        Recipe r;

        // if editing an old recipe
        if(thisIntent.getStringExtra("title") != null && !title.equals("")) {
            try {
                System.out.println("Saving changes to " + thisIntent.getStringExtra("title") + "...");
                r = recipeManager.saveButton(thisIntent.getStringExtra("title"), ingredients, directions, false);

                if(!thisIntent.getStringExtra("title").equals(title)) {
                    System.out.println("Renaming " + thisIntent.getStringExtra("title") + "...");
                    recipeManager.renameRecipe(r, title);
                }
                addRecipeTags(r);
                removeRecipeTags(r);
                System.out.println("Saving Success!");
                Intent i = new Intent(EditRecipe.this, ViewRecipe.class);
                i.putExtra("recipeKey",title);
                startActivity(i);
            }
            catch(RecipeExistenceException e) {
                System.out.println("Saving failed!");
                System.out.println(e);
                Messages.oops(this, "Unable to save as recipe name is already in use, choose a different title!");
            }
        }
        // if creating new recipe
        else if(!title.equals(""))
        {
            try {
                System.out.println("Saving " + title + "...");
                r = recipeManager.saveButton(title, ingredients, directions, true);
                System.out.println("Saving succeeded!");
                addRecipeTags(r);
                removeRecipeTags(r);
                Intent i = new Intent(EditRecipe.this, ViewRecipe.class);
                i.putExtra("recipeKey",title);
                startActivity(i);
            }
            catch(RecipeExistenceException e) {
                System.out.println("Saving failed!");
                System.out.println(e);
                Messages.oops(this, "Unable to save as recipe name is already in use, choose a different title!");
            }
        }
        else
        {
            System.out.println("Abort save, null field found.");
            Messages.oops(this, "Unable to save, this recipe needs a title!");
        }
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

            if(!ingredientName.equals("") && !ingredientCount.equals(""))//remove last two conditions when possible
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
            else if(!ingredientName.equals(""))
            {
                current = new Ingredient(ingredientName);
                ingredients.add(current);
            }
        }

        return ingredients;
    }

    //put retrieval methods in separate class?
    private ArrayList<Direction> getDirections(View view)
    {
        ArrayList<Direction> directions = new ArrayList<>(0);
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
        EditText recipeTitle = findViewById(R.id.recipeTitle);
        recipeTitle.setText(title);
        Recipe populater = recipeFetcher.getRecipeByName(title);//use to populate fields
        ViewGroup curIng = findViewById(R.id.Ingredient);
        ViewGroup curDir = findViewById(R.id.Direction);
        if(populater.getIngredients() != null){
            for(Ingredient ing:populater.getIngredients())
            {
                name = curIng.findViewById(R.id.IngredientName);
                name.setText(ing.getName());
                amount = curIng.findViewById(R.id.IngredientAmount);
                amount.setText(ing.getAmt().getAmtStr());
                //figure out how to get the unit used by the current amount, and compare it to spinner values.
                unitList = curIng.findViewById(R.id.unitList);
                unitList.setSelection(units.getPosition(ing.getAmt().getUnit()));
                curIng = findViewById(this.addIngredient(findViewById(R.id.IngredientContainer)));
                Log.d("id", "" + curIng.getId());
            }
        }
        if(populater.getDirections() != null) {
            for(Direction dir:populater.getDirections())
            {
                name = curDir.findViewById(R.id.DirectionName);
                name.setText(dir.getName());
                time = (EditText) curDir.findViewById(R.id.TimeEstimate);
                time.setText(dir.getTime() + "");
                EditText contents = (EditText) curDir.findViewById(R.id.textbox);
                contents.setText(dir.getText());
                curDir = findViewById(this.addDirection(findViewById(R.id.DirectionContainer)));
            }
        }
    }

    public int addDirection(View view)
    {
        LinearLayout instructionContainer = findViewById(R.id.DirectionContainer);
        View child = getLayoutInflater().inflate(R.layout.instruction_field, null);
        instructionContainer.addView(child);
        child.setId(View.generateViewId());

        View deleteDirectionButton = child.findViewById(R.id.DirectionDeleteButton);
        deleteDirectionButton.setOnClickListener(this::removeDirection);

        return child.getId();
    }

    public int addIngredient(View view)
    {
        LinearLayout ingredientContainer = findViewById(R.id.IngredientContainer);
        View child = getLayoutInflater().inflate(R.layout.ingredient_field, null);
        ingredientContainer.addView(child);
        child.setId(View.generateViewId());

        View deleteIngredientButton = child.findViewById(R.id.IngredientDeleteButton);
        deleteIngredientButton.setOnClickListener(this::removeIngredient);

        Spinner ingDrop = child.findViewById(R.id.unitList);
        ArrayAdapter<String> units = new ArrayAdapter<String>(EditRecipe.this, android.R.layout.simple_spinner_item, Units.unitList());
        units.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ingDrop.setAdapter(units);

        return child.getId();
    }


    public void removeDirection(View view)
    {
        LinearLayout ingredientContainer = findViewById(R.id.DirectionContainer);
        ingredientContainer.removeView((View) view.getParent().getParent());
    }

    public void removeIngredient(View view)
    {
        LinearLayout ingredientContainer = findViewById(R.id.IngredientContainer);
        ingredientContainer.removeView((View) view.getParent().getParent());
    }

    public void deleteRecipe(View view){
        Intent thisIntent = getIntent();
        String title = getTitle(view);
        if(thisIntent.getStringExtra("title") != null && !title.equals("")) {
            showNoticeDialog();
        }
        else{
            Messages.oops(this,"This recipe doesn't exist!");
        }

    }

    private void deleteConfirmed(View v){
        Intent thisIntent = getIntent();
        try{
            recipeManager.delRecipe(recipeFetcher.getRecipeByName(thisIntent.getStringExtra("title")));
        } catch (RecipeExistenceException e) {
            e.printStackTrace();
        }
        Intent i = new Intent(EditRecipe.this, RecipeBrowser.class);
        startActivity(i);
    }

    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new NoticeDialogFragment();
        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        dialog.dismiss();
        deleteConfirmed(dialog.getView());
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    @Override
    public void onBackPressed() {

        Intent thisIntent = getIntent();
        Intent i;
        // if editing an old recipe
        if (thisIntent.getStringExtra("title") != null) {
            i = new Intent(EditRecipe.this, ViewRecipe.class);
        }
        else {
            i = new Intent(EditRecipe.this, RecipeBrowser.class);
        }
        Log.d("CDA", "onBackPressed Called");
        startActivity(i);
    }

    private boolean navigation(MenuItem item) {
        if (item.getItemId() == R.id.new_recipe_button) {
            return true;
        } else if (item.getItemId() == R.id.browse_recipe_button) {
            Intent i = new Intent(EditRecipe.this, RecipeBrowser.class);
            startActivity(i);
            return true;
        } else if (item.getItemId() == R.id.current_recipe_button) {
            Intent i = new Intent(EditRecipe.this, ViewRecipe.class);
            startActivity(i);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void populateTags(){
        Flow tags = findViewById(R.id.add_recipe_tags);
        ConstraintLayout parent = (ConstraintLayout) findViewById(R.id.tagConstraintEdit);

        String[] tagList = tagHandler.fetchTags();

        int [] idList = new int[tagList.length];
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

            if(recipe != null) {
                b.setChecked(recipe.hasTag(s));
            }

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
            b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton bv, boolean isChecked) {
                    if (isChecked) {
                        addTag(bv.getText().toString());
                        //tagHandler.addTagToRecipe(recipeFetcher.getRecipeByName(getIntent().getStringExtra("title")), bv.getText().toString());
                    } else {
                        removeTag(bv.getText().toString());
                        //tagHandler.removeTagFromRecipe(recipeFetcher.getRecipeByName(getIntent().getStringExtra("title")), bv.getText().toString());
                    }
                }
            });

        }
        tags.setReferencedIds(idList);
    }

    private void addTag(String t){
        if(!tags.contains(t))   {
            tags.add(t);
        }
    }
    private void removeTag(String t){
        if(!oldTags.contains(t)){
            oldTags.add(t);
        }
        if(tags.contains(t)){
            tags.remove(t);
        }
    }

    private void addRecipeTags(Recipe r){
        for (String s:tags){
            tagHandler.addTagToRecipe(r, s);
        }
    }

    private void removeRecipeTags(Recipe r){
        for (String s:oldTags){
            tagHandler.removeTagFromRecipe(r,s);
        }
    }


}
