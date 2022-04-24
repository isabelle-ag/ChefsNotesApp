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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_view);

        ImageView recipeImg = (ImageView) findViewById(R.id.recipe_photo);
//        View root = recipeImg.getRootView();
//        int rootid = root.getId();
//        Log.e("Root:", "Root View ID: "+rootid);
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        //photoView.rootView.findViewById(R.id.*name_of_the_view*)
                        //this.getCurrentFocus().getRootView().findViewById(R.id.recipeName);
                        //String filename = saveImage(uri);
                        Bitmap bmap = null;
                        try {
                            bmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
                            Log.e("BMAP", "Bitmap has been set");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String name = saveBMap(bmap);
                        String path = File.separatorChar+name;
                        path = getFilesDir() + path;
                        File imgFile = new  File(path);
                        //Log.e("mGetContent", "Path name: "+path);

                        if(imgFile.exists()){
                            //Log.e("mGetContent", "ImgFile exists!! "+path);
                            //Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            Bitmap myBitmap= loadImageBitmap(name);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                            Glide.with(recipeImg)
//                                    .load(myBitmap)
//                                    .dontTransform()
//                                    .into(recipeImg);
                            recipeImg.setImageURI(uri);
                            ViewRecipe view = new ViewRecipe();
                            //view.setUri(uri);
                        }
                        else{
                            Log.e("Set View", "Trying to set recipeImg");
                            recipeImg.setImageBitmap(bmap);
                        }

//                        MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
//                       // Bitmap bitmap = getBitmap();
//                        recipeImg.setImageResource();
                    }
                });

    }

    public void choosePic(View v){
        Log.e("OnClick", "Inside on click listener for image view");
        mGetContent.launch("image/*");
    }

//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        //File storageDir = getFilesDir();
////        File image = File.createTempFile(
////                imageFileName,  /* prefix */
////                ".jpg",         /* suffix */
////                storageDir      /* directory */
////        );
//
//        // Save a file: path for use with ACTION_VIEW intents
////        String currentPhotoPath = image.getAbsolutePath();
////        return image;
//        return null;
//    }

    private String saveImage(Uri imgUri){
        Log.e("saveImage", "Inside saveImage");
        String sourceFilename= imgUri.getPath();
        String uniqueName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String destinationFilename = android.os.Environment.getExternalStorageDirectory().getPath()+File.separatorChar+uniqueName+".jpg";

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(new FileInputStream(sourceFilename));
            bos = new BufferedOutputStream(new FileOutputStream(destinationFilename, false));
            byte[] buf = new byte[1024];
            bis.read(buf);
            do {
                bos.write(buf);
            } while(bis.read(buf) != -1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) bis.close();
                if (bos != null) bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return destinationFilename;
    }

    private String saveBMap(Bitmap bmap) {
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg";
        //String destinationFilename = android.os.Environment.getExternalStorageDirectory().getPath() + File.separatorChar + name;
        //ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        //File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        //Log.e("INFO", "File directory is: "+directory);
        // Create imageDir
        //File mypath = new File(directory,name);
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


    public Bitmap loadImageBitmap(String name){
        FileInputStream fileInputStream;
        Bitmap bitmap = null;
        try{
            fileInputStream = this.openFileInput(name);
            bitmap = BitmapFactory.decodeStream(fileInputStream);
            fileInputStream.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

//        try {
//            fos = new FileOutputStream(mypath);
//            Log.e("Good", "fos opened! File path: "+mypath);
//            // Use the compress method on the BitMap object to write image to the OutputStream
//            bmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                fos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }}

        //return directory.getAbsolutePath();


}
