package comp3350.chefsnotes.presentation;

import static android.widget.LinearLayout.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import java.util.ArrayList;

import comp3350.chefsnotes.R;
import comp3350.chefsnotes.objects.TagExistenceException;
import comp3350.chefsnotes.persistence.FakeTagDB;
import comp3350.chefsnotes.persistence.TagDBMSTools;

public class RecipeBrowser extends AppCompatActivity {
    TagDBMSTools tagDB = new FakeTagDB();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_browser);


        LinearLayout tags = findViewById(R.id.filterTagLayout);


        for(int i=0;i<5;i++){
            try {
                tagDB.addTag("Asian");
                tagDB.addTag("American");
                tagDB.addTag("Fusion");
                tagDB.addTag("Vegan");
                tagDB.addTag("Mexican");
            } catch (TagExistenceException e) {
                e.printStackTrace();
            }

        }

        String[] tagList = tagDB.tagList();

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
            b.setMaxWidth(150);
            b.setPadding(10,5,10,5);




            b.setId(b.generateViewId());

            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.togglebutton_selector, null);
            ViewCompat.setBackground(b, drawable);

            //b.setBackground(getResources().getDrawable(R.drawable.togglebutton_selector));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMarginStart(8);
            params.setMarginEnd(8);
            b.setLayoutParams(params);
            // LinearLayout.LayoutParams bParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            // b.setLayoutParams(bParams);


            tags.addView(b);
            b.setOnClickListener(v -> setFilterCondition(v));


        }
    }

    private void setFilterCondition(View v){};


}