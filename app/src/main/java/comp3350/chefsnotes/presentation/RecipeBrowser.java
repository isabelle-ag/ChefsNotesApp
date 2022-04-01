package comp3350.chefsnotes.presentation;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import comp3350.chefsnotes.R;
import comp3350.chefsnotes.application.Services;
import comp3350.chefsnotes.business.IRecipeFetcher;
import comp3350.chefsnotes.business.ITagHandler;
import comp3350.chefsnotes.business.RecipeFetcher;
import comp3350.chefsnotes.business.TagHandler;


public class RecipeBrowser extends AppCompatActivity {
    private IRecipeFetcher recipeFetcher = new RecipeFetcher(Services.getRecipePersistence());//refactor to use services natively
    private ITagHandler tagHandler = new TagHandler(Services.getTagPersistence(), Services.getRecipePersistence());
    private ArrayList<String> tagFilters;
    private ArrayList<String> excludedTags;
    private String searchTerm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_browser);
        tagFilters = new ArrayList<>();
        excludedTags = new ArrayList<>();
        searchTerm = "";

        EditText searchBox = (EditText) findViewById(R.id.searchRecipeName);
        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
        navView.setOnItemSelectedListener(this::navigation);

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                searchTerm = s.toString().trim();
                populateRecipes(searchTerm);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        populateTags();
        populateRecipes("");

    }

    @Override
    protected void onResume() {
        super.onResume();

        populateRecipes("");
    }



    private void populateRecipes(String searchTerm){
        ListView searchResults = (ListView) findViewById(R.id.results);
        String[] recipeList;
        String[] exTagArray = new String[0];
        if(tagFilters.size() > 0){
            String[] incTagArray = new String[tagFilters.size()];
            incTagArray = tagFilters.toArray(incTagArray);
            if(excludedTags.size()>0) {
                exTagArray = new String[excludedTags.size()];
                exTagArray = excludedTags.toArray(exTagArray);
            }
            recipeList = recipeFetcher.filterRecipeNamesByTags(incTagArray, exTagArray, recipeFetcher.getRecipesByText(searchTerm));
        }
        else{
            recipeList = recipeFetcher.getRecipeNamesByText(searchTerm);
        }


        ArrayAdapter<String> rAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, recipeList);
        searchResults.setAdapter(rAdapter);
        searchResults.setOnItemClickListener((parent, v, position, id) -> {
            String recipeName = (String) parent.getItemAtPosition(position);
            Intent i = new Intent(RecipeBrowser.this, ViewRecipe.class);
            i.putExtra("recipeKey",recipeName);
            startActivity(i);
        });
    }


    private void populateTags() {

        Flow tags = findViewById(R.id.filterTagLayout);
        ConstraintLayout parent = (ConstraintLayout) findViewById(R.id.tagConstraint);

        String[] tagList = tagHandler.fetchTags();

        int[] idList = new int[tagList.length];
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
                    if(isChecked) {
                        addTag(bv.getText().toString());
                    } else {
                        removeTag(bv.getText().toString());
                    }
                    populateRecipes(searchTerm);
                }
            });
            tags.setReferencedIds(idList);
        }
    }


    private void addTag(String t) {
        if (!tagFilters.contains(t)) {
            tagFilters.add(t);
        }
    }

    private void removeTag(String t){
        if(tagFilters.contains(t)){
        tagFilters.remove(t);
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(RecipeBrowser.this, MainActivity.class);
        Log.d("CDA", "onBackPressed Called");
        startActivity(i);
    }

    private boolean navigation(MenuItem item){
        if(item.getItemId() == R.id.new_recipe_button){
            Intent i = new Intent(RecipeBrowser.this, EditRecipe.class);
            startActivity(i);
            return true;
        }
        else if(item.getItemId() == R.id.browse_recipe_button){
            return true;
        }
        else if(item.getItemId() == R.id.current_recipe_button){
            Intent i = new Intent(RecipeBrowser.this, ViewRecipe.class);
            startActivity(i);
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }



}