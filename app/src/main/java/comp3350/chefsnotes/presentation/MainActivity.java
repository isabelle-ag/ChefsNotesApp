// includes adaptations from SampleProject's HomeActivity.java

package comp3350.chefsnotes.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import comp3350.chefsnotes.R;
import comp3350.chefsnotes.application.Main;
import comp3350.chefsnotes.application.Services;
import comp3350.chefsnotes.business.IRecipeFetcher;
import comp3350.chefsnotes.business.IRecipeManager;
import comp3350.chefsnotes.business.RecipeFetcher;
import comp3350.chefsnotes.business.RecipeManager;
import comp3350.chefsnotes.objects.Photo;
import comp3350.chefsnotes.objects.Recipe;
import comp3350.chefsnotes.objects.SampleRecipeGenerator;
import comp3350.chefsnotes.objects.TagExistenceException;
import comp3350.chefsnotes.persistence.DBMSTools;
import comp3350.chefsnotes.persistence.PhotoDBMSTools;
import comp3350.chefsnotes.persistence.PhotoList;
import comp3350.chefsnotes.persistence.TagDBMSTools;

public class MainActivity extends AppCompatActivity {

    public static final boolean DB_MODE = Services.MODE_REAL; // set flag for real/false databases

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        copyDatabaseToDevice();
        // instantiate databases
        Services.getTagPersistence(DB_MODE);
        createTags();

        Services.getPhotoPersistence(DB_MODE);
        setupPhotoList();

        DBMSTools dbSetup = Services.getRecipePersistence(DB_MODE);
        if (dbSetup.getAllRecipes().length == 0) {
            Recipe[] samples = SampleRecipeGenerator.sampleList();
            for (Recipe curr : samples) {
                boolean insert = dbSetup.createRecipe(curr.getTitle());
                if(insert){
                    dbSetup.commitChanges(curr);
                }
            }
            getDefaultPhotos();
        }


        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
        navView.setSelectedItemId(R.id.browse_recipe_button);
        navView.setOnItemSelectedListener(this::navigation);
    }

    public void makeRecipe(View view) {
        Intent switchActivityIntent = new Intent(this, EditRecipe.class);
        switchActivityIntent.putExtra("recipeKey", (String) null);
        startActivity(switchActivityIntent);
    }

    // establishes a directory for the working database
    private void copyDatabaseToDevice() {
        final String DB_PATH = "db";

        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try {

            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);

            Main.setDBPathName(dataDirectory.toString() + "/" + Main.getDBPathName());

        } catch (final IOException ioe) {
            Messages.warning(this, "Unable to access application data: " + ioe.getMessage());
        }
    }

    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];

            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }

    public void browseRecipes(View view) {
        Intent switchActivityIntent = new Intent(this, RecipeBrowser.class);
        startActivity(switchActivityIntent);
    }

    private boolean navigation(MenuItem item){
        if(item.getItemId() == R.id.new_recipe_button){
            Intent i = new Intent(MainActivity.this, EditRecipe.class);
            startActivity(i);
            return true;
        }
        else if(item.getItemId() == R.id.browse_recipe_button){
            Intent i = new Intent(MainActivity.this, RecipeBrowser.class);
            startActivity(i);
            return true;
        }
        else if(item.getItemId() == R.id.current_recipe_button){
            Intent i = new Intent(MainActivity.this, ViewRecipe.class);
            startActivity(i);
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }

    private void createTags() {
        TagDBMSTools tagdb = Services.getTagPersistence();
        if (tagdb.tagList().length == 0) {
            try {
                tagdb.addTag("African");
                tagdb.addTag("American");
                tagdb.addTag("Asian");
                tagdb.addTag("Chinese");
                tagdb.addTag("French");
                tagdb.addTag("Fusion");
                tagdb.addTag("German");
                tagdb.addTag("Greek");
                tagdb.addTag("Indian");
                tagdb.addTag("Mexican");
                tagdb.addTag("South American");
                tagdb.addTag("Ukrainian");
                tagdb.addTag("Keto");
                tagdb.addTag("Pescatarian");
                tagdb.addTag("Savory");
                tagdb.addTag("Spicy");
                tagdb.addTag("Sweet");
                tagdb.addTag("Vegan");
                tagdb.addTag("Vegetarian");

            } catch (TagExistenceException e) {
                e.printStackTrace();
            }

        }
    }

    private void setupPhotoList(){
        PhotoDBMSTools photodb = Services.getPhotoPersistence();
        PhotoList pl = Services.getPhotoList();
        Photo[] stored = photodb.getAllPhotos();

        if(pl.isEmpty())
        {
            for(Photo i : stored){
                assert pl.addPhoto(i); // should be no failures.
            }
        }

    }

    private void getDefaultPhotos(){
        String dest = getFilesDir().toString() + File.separatorChar;
        String coStr = "cookies.jpg";
        String co2Str = "cookie_dough.jpg";
        String ch1Str = "butter_chicken.jpg";
        String ch2Str = "chicken.jpg";
        String whistle = "tomato_soup.jpg";
        int cookie1 = R.drawable.cookies;
        int cookie2 = R.drawable.cookie_dough;
        int chikn1 = R.drawable.butter_chicken;
        int chikn2 = R.drawable.chicken;
        int whistle3 = R.drawable.tomato_soup;

        String[] allNames = {coStr, co2Str, ch1Str, ch2Str, whistle};
        int[] allImgs = {cookie1, cookie2, chikn1, chikn2, whistle3};
        FileOutputStream fileOutputStream;
        int i = 0;
        for(int id : allImgs) {
            Bitmap bmap = BitmapFactory.decodeResource(getResources(), id);;
            try {
                fileOutputStream = this.openFileOutput(allNames[i], Context.MODE_PRIVATE);
                i++;
                bmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            IRecipeManager recipeManager = new RecipeManager(Services.getRecipePersistence());
            IRecipeFetcher recipeFetcher = new RecipeFetcher(Services.getRecipePersistence());
            Recipe recipe = recipeFetcher.getRecipeByName("Chocolate Chip Cookies");
            recipeManager.addPhoto(recipe, (dest+coStr));
            recipeManager.addPhoto(recipe, (dest+co2Str));

            recipe = recipeFetcher.getRecipeByName("Indian Butter Chicken");
            recipeManager.addPhoto(recipe, (dest+ch1Str));
            recipeManager.addPhoto(recipe, (dest+ch2Str));

            recipe = recipeFetcher.getRecipeByName("Tomato Soup");
            recipeManager.addPhoto(recipe, (dest+whistle));
        }
    }


}
