package comp3350.chefsnotes.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ToggleButton;
import android.view.View;

import java.util.ArrayList;

import comp3350.chefsnotes.R;

public class FilterTagView extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_search);
        LinearLayout tags = findViewById(R.id.filterTagLayout);

        ArrayList<ToggleButton> ab = new ArrayList<ToggleButton>();
        for(int i=0;i<5;i++){
            ab.add(new ToggleButton(this));
        }
        for (ToggleButton b : ab) {
            tags.addView(b);
            b.setTextOn("Hi");
            b.setTextOff("Hi");
            b.setTextSize(11);

            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.togglebutton_selector, null);
            ViewCompat.setBackground(b, drawable);

            //b.setBackground(getResources().getDrawable(R.drawable.togglebutton_selector));
            b.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
           // LinearLayout.LayoutParams bParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
           // b.setLayoutParams(bParams);
            b.setOnClickListener(v -> setFilterCondition(v));
        }
        setContentView(R.layout.recipe_search);
    }

    private void setFilterCondition(View v){};
}
