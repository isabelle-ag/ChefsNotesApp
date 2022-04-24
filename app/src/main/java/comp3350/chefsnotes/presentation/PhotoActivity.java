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

public class PhotoActivity extends AppCompatActivity  {
    private ActivityResultLauncher<String> mGetContent;
    private Uri uri;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_view);

        ImageView recipeImg = (ImageView) findViewById(R.id.recipe_photo);
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
                            String path = File.separatorChar + name;
                            path = getFilesDir() + path;
                            Log.e("PHOTOS", "Im here");
                            setUri(uri);
                            setPath(path);
                            Log.e("PHOTOS", "NOW here");
                        }
                    }
                });

    }

    public void choosePic(View v){
        Log.e("OnClick", "Inside on click listener for image view");
        mGetContent.launch("image/*");
    }

    private void setUri(Uri uri){
        this.uri = uri;
    }

    public Uri getUri(){
        return this.uri;
    }

    private void setPath(String p){this.path = p;}

    public String getPath(){ return this.path; }

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
