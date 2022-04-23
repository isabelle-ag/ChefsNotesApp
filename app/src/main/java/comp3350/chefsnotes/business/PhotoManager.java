package comp3350.chefsnotes.business;

import android.content.Intent;
import android.net.Uri;

public class PhotoManager implements IPhotoManager{

    public Uri getPhoto(){
        Intent viewGallery = new Intent(Intent.ACTION_PICK);
        viewGallery.setType("image/*");
        return null;
    }
}
