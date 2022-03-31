package comp3350.chefsnotes.presentation;
import androidx.appcompat.app.AppCompatActivity;

//import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.constraintlayout.helper.widget.Flow;

import comp3350.chefsnotes.R;
import comp3350.chefsnotes.application.Services;
import comp3350.chefsnotes.business.ITagHandler;
import comp3350.chefsnotes.business.TagHandler;
import comp3350.chefsnotes.objects.TagExistenceException;
import comp3350.chefsnotes.persistence.DBMSTools;
import comp3350.chefsnotes.persistence.FakeTagDB;
import comp3350.chefsnotes.persistence.TagDBMSTools;
import comp3350.chefsnotes.persistence.TagPersistence;


public class RecipeBrowser extends AppCompatActivity {
    DBMSTools db;
    TagDBMSTools tagDB;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_browser);

        db = Services.getRecipePersistence();
        tagDB = Services.getTagPersistence();



        EditText searchBox = (EditText) findViewById(R.id.searchRecipeName);

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                populateRecipes(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        populateTags();
        populateRecipes("");

        }

    private void setFilterCondition(View v){

    }


    private void populateRecipes(String searchTerm){
        String[] recipeList;

        ListView searchResults = (ListView) findViewById(R.id.results);
        if (searchTerm == "") {
            recipeList = db.getRecipeNames();
        }
        else {
            recipeList = db.searchRecipeNames(searchTerm);
        }


        ArrayAdapter<String> rAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, recipeList);
        searchResults.setAdapter(rAdapter);
        searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String recipeName = (String) parent.getItemAtPosition(position);
                Intent i = new Intent(RecipeBrowser.this, ViewRecipe.class);
                i.putExtra("recipeKey",recipeName);
                startActivity(i);
            }
        });
    }


    private void populateTags(){

        Flow tags = findViewById(R.id.filterTagLayout);
        ConstraintLayout parent = (ConstraintLayout) findViewById(R.id.tagConstraint);

        String[] tagList = tagDB.tagList();

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
            b.setOnClickListener(v -> setFilterCondition(v));
        }
        tags.setReferencedIds(idList);
    }


}