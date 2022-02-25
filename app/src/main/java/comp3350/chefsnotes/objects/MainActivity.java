package comp3350.chefsnotes.objects;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

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
}
