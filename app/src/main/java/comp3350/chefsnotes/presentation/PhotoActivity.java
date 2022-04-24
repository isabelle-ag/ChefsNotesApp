package comp3350.chefsnotes.presentation;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
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
    private Recipe recipe;
    private final IRecipeManager recipeManager = new RecipeManager(Services.getRecipePersistence());
    private int currImg;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_view);
        currImg = 0;

        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        Bitmap bmap = null;
                        if(uri != null) {
                            try {
                                if(Build.VERSION.SDK_INT < 28) {
                                    bmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
                                }
                                else{
                                    ImageDecoder.Source source = ImageDecoder.createSource(getApplicationContext().getContentResolver(), uri);
                                    bmap = ImageDecoder.decodeBitmap(source);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            String name = saveBMap(bmap);
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Photo added.",
                                    Toast.LENGTH_SHORT);
                            toast.show();
                            go(name, uri);
                        }
                    }
                });

    }

    @Override
    protected void onResume(){
        super.onResume();
        currImg = 0;
    }

    public void choosePic(View v){
        String[] photos = recipeManager.getPhotos(recipe);
        if(photos.length != 0){
            return;
        }
        else {
            mGetContent.launch("image/*");
        }
    }

    public void addPic(View v){
        mGetContent.launch("image/*");
    }


    private void go(String name, Uri uri){
        ImageView img = (ImageView) findViewById(R.id.recipe_photo);
        img.setImageURI(uri);
        String path = File.separatorChar + name;
        path = getFilesDir() + path;
        if(path != null) {
            recipeManager.addPhoto(recipe, path);
            String[] photos = recipeManager.getPhotos(recipe);
            currImg = photos.length-1;
            setImg();
        }
    }

    private String saveBMap(Bitmap bmap) {
    String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg";

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

    protected void lastImg(View v){
        String[] photos = recipeManager.getPhotos(recipe);
        if(photos.length < 2){
            return;
        }
        if (currImg == 0){
            currImg = photos.length;
        }
        currImg--;
        setImg();
    }

    protected void nextImg(View v){
        String[] photos = recipeManager.getPhotos(recipe);
        if(photos.length < 2){
            return;
        }
        if (currImg == photos.length-1){
            currImg = 0;
        }
        else {
            currImg++;
        }
        setImg();
    }

    public void setImg(){
        String[] photos = recipeManager.getPhotos(recipe);
        ImageView recipePhotos = (ImageView) findViewById(R.id.recipe_photo);
        if(photos.length > currImg) {
            Uri imgUri = Uri.parse(photos[currImg]);
            recipePhotos.setImageURI(imgUri);
            recipePhotos.setClickable(false);
        }
        else if (photos.length == 0){
            recipePhotos.setImageResource(R.drawable.camera);
            recipePhotos.setClickable(true);
            currImg = 0;
        }
        else{
            currImg = 0;
            setImg();
        }
    }

    public void setRecipe(Recipe r){ this.recipe = r;}

    public void delPhoto(View v){
        String[] photos = recipeManager.getPhotos(recipe);
        if(photos.length <=0){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No photos found",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            confirmDel();
        }
    }

    private void confirmDel(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete this photo? This is immediate and irreversible.");

// Set up the buttons
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void delete(){
        String[] photos = recipeManager.getPhotos(recipe);
        if(photos.length >0) {
            String path = photos[currImg];
            recipeManager.delPhoto(recipe, path);
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Photo deleted.",
                    Toast.LENGTH_SHORT);
            toast.show();
            photos = recipeManager.getPhotos(recipe);
            if (photos.length == 0) {
                setImg();
            } else if (currImg == 0) {
                currImg = photos.length - 1;
            } else {
                currImg--;
            }
            setImg();
        }
    }

    public void refresh(){
        currImg = 0;
    }



}
