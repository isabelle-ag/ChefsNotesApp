package comp3350.chefsnotes.presentation;

import androidx.constraintlayout.helper.widget.Flow;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;

import android.content.ClipData;
import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.content.ClipboardManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import comp3350.chefsnotes.BuildConfig;
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
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;

public class ViewRecipe extends PhotoActivity {

    private final IRecipeFetcher recipeFetcher = new RecipeFetcher(Services.getRecipePersistence());
    private final IRecipeManager recipeManager = new RecipeManager(Services.getRecipePersistence());
    private Recipe recipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);
        super.refresh();

        ImageButton editButton = findViewById(R.id.edit_button);
        ImageButton copyButton = findViewById(R.id.copy_button);
        ImageView shareButton = findViewById(R.id.share_button);
        ImageView recipeImg = (ImageView) findViewById(R.id.recipe_photo);
        ImageButton prevButton = findViewById(R.id.prev_photo);
        ImageButton nextButton = findViewById(R.id.next_photo);
        ImageButton addPhoto = findViewById(R.id.add_photo);

        View delPhoto = findViewById(R.id.delete_photo);
        delPhoto.setVisibility(View.INVISIBLE);

        editButton.setOnClickListener(this::editRecipe);
        copyButton.setOnClickListener(this::copyRecipe);
        shareButton.setOnClickListener(this::exportRecipe);
        recipeImg.setOnClickListener(super::choosePic);
        prevButton.setOnClickListener(super::lastImg);
        nextButton.setOnClickListener(super::nextImg);
        addPhoto.setOnClickListener(super::addPic);

        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
        navView.setOnItemSelectedListener(this::navigation);
        navView.setSelectedItemId(R.id.current_recipe_button);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String recipeName = extras.getString("recipeKey");
            recipe = recipeFetcher.getRecipeByName(recipeName);
        } else {
            recipe = recipeFetcher.getRecentRecipe();
        }

        if (recipe != null) {
            super.setRecipe(recipe);
            fillViewer();
            populateTags(recipe);
        } else {
            errorScreen();
        }

    }


    @Override
    protected void onStop() {
        super.onStop();
        if(this.recipe != null) {
            TextView notes = findViewById(R.id.Notes);
            recipeManager.updateNotes(recipe, notes.getText().toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        super.refresh();

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
        switchActivityIntent.putExtra("recipeKey", recipe.getTitle());
        startActivity(switchActivityIntent);
    }

    private void copyRecipe(View view) {
        //perform action to populate recipe - must be added somewhere
        IRecipeManager manager = new RecipeManager(Services.getRecipePersistence());
        String title;
        Intent switchActivityIntent = new Intent(this, EditRecipe.class);

        title = manager.copyRecipe(recipe, null).getTitle();
        switchActivityIntent.putExtra("recipeKey", title);
        startActivity(switchActivityIntent);
    }

    private void exportRecipe(View view) {
        ClipboardManager clipManager = (ClipboardManager)getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);;
        clipManager.setPrimaryClip(ClipData.newPlainText(null, recipe.stringExport()));
        Toast toast = Toast.makeText(getApplicationContext(),
                "Recipe saved to clipboard",
                Toast.LENGTH_SHORT);
        toast.show();

    }

    private void fillViewer() {
        String[] directionsTemp = recipe.getDirectionStrings();
        String[] ingredients = recipe.getIngredientStrings();
        String title = recipe.getTitle();
        String time = directionsTemp[0];
        super.setImg();

        String[] directions = Arrays.copyOfRange(directionsTemp, 1, directionsTemp.length);

        ((TextView) findViewById(R.id.recipeName)).setText(title);

        //Ingredient List
        ArrayAdapter<String> ingAdapter = new ArrayAdapter<>(this,
                R.layout.list_style, ingredients);
        // ListView ingView = findViewById(R.id.ingredientListView);
        ((ListView) findViewById(R.id.ingredientListView)).setAdapter(ingAdapter);

        //Direction List
        ArrayAdapter<String> dirAdapter = new ArrayAdapter<>(this,
                R.layout.list_style, directions);
        //ListView dirView = findViewById(R.id.directionListView);
        ((ListView) findViewById(R.id.directionListView)).setAdapter(dirAdapter);

        ((TextView) findViewById(R.id.totalTimeView)).setText(time);

        TextView notes = findViewById(R.id.Notes);
        notes.setText(recipeManager.loadNotes(recipe));
    }

    private void errorScreen() {
        //valid = false;
        String error = "Invalid Recipe. Try making a new one or open one from the browser.";
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
            item.setChecked(true);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void populateTags(Recipe r) {
        ITagHandler tagHandler = new TagHandler(Services.getTagPersistence(), Services.getRecipePersistence());

        Flow tags = findViewById(R.id.viewTags);
        ConstraintLayout parent = (ConstraintLayout) findViewById(R.id.tagContainerView);

        ArrayList<String> tagList = r.getTags();

        int[] idList = new int[tagList.size()];
        int i = 0;

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


            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.rounded_button, null);

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


//    private void lastImg(View v){
//
//    }
//    private void nextImg(View v){
//
//    }

}

