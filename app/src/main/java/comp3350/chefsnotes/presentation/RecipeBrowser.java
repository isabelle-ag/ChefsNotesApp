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
import comp3350.chefsnotes.persistence.TagDBMSTools;

public class RecipeBrowser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_browser);


        LinearLayout tags = findViewById(R.id.filterTagLayout);

        ArrayList<ToggleButton> ab = new ArrayList<ToggleButton>();
        for(int i=0;i<5;i++){
            ab.add(new ToggleButton(this));
        }
        for (ToggleButton b : ab) {

            b.setTextOn("Hi");
            b.setTextOff("Hi");
            b.setTextSize(15);


            b.setId(b.generateViewId());

            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.togglebutton_selector, null);
            ViewCompat.setBackground(b, drawable);

            //b.setBackground(getResources().getDrawable(R.drawable.togglebutton_selector));
            b.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            // LinearLayout.LayoutParams bParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            // b.setLayoutParams(bParams);


            tags.addView(b);
            b.setOnClickListener(v -> setFilterCondition(v));


        }
    }

    private void setFilterCondition(View v){};


}