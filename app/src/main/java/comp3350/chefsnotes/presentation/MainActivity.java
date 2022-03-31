// includes adaptations from SampleProject's HomeActivity.java

package comp3350.chefsnotes.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import comp3350.chefsnotes.R;
import comp3350.chefsnotes.application.Main;
import comp3350.chefsnotes.application.Services;
import comp3350.chefsnotes.objects.Recipe;
import comp3350.chefsnotes.objects.SampleRecipe;
import comp3350.chefsnotes.objects.TagExistenceException;
import comp3350.chefsnotes.persistence.DBMSTools;
import comp3350.chefsnotes.persistence.TagDBMSTools;

public class MainActivity extends AppCompatActivity {

    public static final boolean DB_MODE = Services.MODE_FAKE; // set flag for real/false databases

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        copyDatabaseToDevice();
        // instantiate databases
        Services.getTagPersistence(DB_MODE);
        DBMSTools dbSetup = Services.getRecipePersistence(DB_MODE);
        Recipe sample = new SampleRecipe();
        if (dbSetup.getRecipe(sample.getTitle()) == null) {
            dbSetup.createRecipe(sample.getTitle());
            dbSetup.commitChanges(sample);
        }

        createTags();
    }

    public void makeRecipe(View view) {
        Intent switchActivityIntent = new Intent(this, EditRecipe.class);
        switchActivityIntent.putExtra("title", (String) null);
        startActivity(switchActivityIntent);
    }

    public void viewRecipe(View view) {
        Intent switchActivityIntent = new Intent(this, ViewRecipe.class);
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

    private void createTags() {
        TagDBMSTools tagdb = Services.getTagPersistence();
        if (tagdb.tagList().length == 0) {
            try {
                tagdb.addTag("African");
                tagdb.addTag("American");
                tagdb.addTag("Asian");
                tagdb.addTag("Chinese");
                tagdb.addTag("Fusion");
                tagdb.addTag("German");
                tagdb.addTag("Greek");
                tagdb.addTag("Mexican");
                tagdb.addTag("South American");
                tagdb.addTag("Ukrainian");
                tagdb.addTag("Keto");
                tagdb.addTag("Pescatarian");
                tagdb.addTag("Vegan");
                tagdb.addTag("Vegetarian");

            } catch (TagExistenceException e) {
                e.printStackTrace();
            }

        }
    }

}
