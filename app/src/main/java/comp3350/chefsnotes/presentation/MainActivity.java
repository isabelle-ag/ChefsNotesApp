package comp3350.chefsnotes.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import comp3350.chefsnotes.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void makeRecipe(View view) {
        Intent switchActivityIntent = new Intent(this, EditRecipe.class);
        startActivity(switchActivityIntent);
    }
}
