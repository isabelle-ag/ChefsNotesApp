package comp3350.chefsnotes.presentation;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import comp3350.chefsnotes.R;
import comp3350.chefsnotes.application.Services;
import comp3350.chefsnotes.business.IRecipeManager;
import comp3350.chefsnotes.business.RecipeManager;
import comp3350.chefsnotes.objects.Recipe;

public class PhotoActivity extends AppCompatActivity  {
    private ActivityResultLauncher<String> mGetContent;
    private Uri uri;
    private Recipe recipe;
    private final IRecipeManager recipeManager = new RecipeManager(Services.getRecipePersistence());

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_view);

        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        Bitmap bmap = null;
                        if(uri != null) {
                            try {
                                bmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            String name = saveBMap(bmap);
                            setUri(uri);
                            go(name);
                        }
                    }
                });

    }

    public void choosePic(View v, Recipe r){
        this.recipe = r;
        mGetContent.launch("image/*");
    }

    private void setUri(Uri uri){
        this.uri = uri;
    }

    private void go(String name){
        ImageView img = (ImageView) findViewById(R.id.recipe_photo);
        img.setImageURI(uri);
        String path = File.separatorChar + name;
        path = getFilesDir() + path;
        Log.e("PHOTOS", "go: path: "+path );
        if(path != null) {
            recipeManager.addPhoto(recipe, path);
        }
    }

    private String saveBMap(Bitmap bmap) {
    String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg";
    Log.e("TAG", "File name: " + name);

    FileOutputStream fileOutputStream;
    try {
        fileOutputStream = this.openFileOutput(name, Context.MODE_PRIVATE);
        bmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
        fileOutputStream.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return name;
}



}
