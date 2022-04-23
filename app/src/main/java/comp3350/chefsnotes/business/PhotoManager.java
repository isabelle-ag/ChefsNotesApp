package comp3350.chefsnotes.business;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoManager implements IPhotoManager{
    public static final int PICK_IMAGE = 1;
    private ActivityResultLauncher<Intent> launcher;

    public void getPhoto() {
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //File storageDir = getFilesDir();
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );

        // Save a file: path for use with ACTION_VIEW intents
//        String currentPhotoPath = image.getAbsolutePath();
//        return image;
        return null;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        //if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        "comp3350.chefsnotes.android.fileprovider",
//                        photoFile);
              //  takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                //startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

        }
    }


//    private File getExternalFilesDir(String directoryPictures) {
//    }


    //Intent viewGallery = new Intent(Intent.ACTION_PICK);
//        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//            @Override
//            public void onActivityResult(ActivityResult result)
//            {
//                if (result.getResultCode() == RESULT_OK) // set button to image
//                {
//                    Uri photoUri = result.getData().getData();
//                }
//            }
//        });
//        Intent viewGallery = new Intent(Intent.ACTION_PICK);//Intent.ACTION_PICK);
//        viewGallery.setType("image/*");
//        launcher.launch(viewGallery);

//        viewGallery.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(viewGallery, GALLERY_IMAGE);

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        if (requestCode == PICK_IMAGE) {
//            //
//        }
//    }

}
