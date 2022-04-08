package comp3350.chefsnotes.application;

import comp3350.chefsnotes.persistence.DBMSTools;
import comp3350.chefsnotes.persistence.FakeDBMS;
import comp3350.chefsnotes.persistence.FakePhotoDB;
import comp3350.chefsnotes.persistence.FakeTagDB;
import comp3350.chefsnotes.persistence.PhotoDBMSTools;
import comp3350.chefsnotes.persistence.PhotoList;
import comp3350.chefsnotes.persistence.PhotoPersistence;
import comp3350.chefsnotes.persistence.RecipePersistence;
import comp3350.chefsnotes.persistence.TagDBMSTools;
import comp3350.chefsnotes.persistence.TagPersistence;
import comp3350.chefsnotes.presentation.MainActivity;

public class Services {
    public static final boolean MODE_FAKE = true;
    public static final boolean MODE_REAL = false;

    private static DBMSTools recipePersistence = null;
    private static TagDBMSTools tagPersistence = null;
    private static PhotoDBMSTools photoPersistence = null;
    private static PhotoList photoList = null;      // for use by Recipe.java only

    // mode only matters on the first call
    public static synchronized DBMSTools getRecipePersistence(boolean mode){    // uhh dependency injection
        if(recipePersistence == null){
            if(mode == MODE_FAKE){
                recipePersistence = new FakeDBMS();
            } else {
                recipePersistence = new RecipePersistence(Main.getDBPathName());
            }
        }

        return recipePersistence;
    }

    public static synchronized DBMSTools getRecipePersistence(){
        return getRecipePersistence(MainActivity.DB_MODE);
    }

    // mode only matters on the first call
    public static synchronized TagDBMSTools getTagPersistence(boolean mode){    // brr dependency injection
        if(tagPersistence == null){
            if(mode == MODE_FAKE){
                tagPersistence = new FakeTagDB();
            } else {
                tagPersistence = new TagPersistence(Main.getDBPathName());
            }
        }

        return tagPersistence;
    }

    public static synchronized TagDBMSTools getTagPersistence(){
        return getTagPersistence(MainActivity.DB_MODE);
    }

    // mode only matters on the first call
    // really only for use by PhotoList as logical decisions are not made within
    public static synchronized PhotoDBMSTools getPhotoPersistence(boolean mode){
        if(photoPersistence == null){
            if(mode == MODE_FAKE){
                photoPersistence = new FakePhotoDB();
            } else {
                photoPersistence = new PhotoPersistence(Main.getDBPathName());
            }
        }

        return photoPersistence;
    }

    // really only for use by PhotoList as logical decisions are not made within
    public static synchronized PhotoDBMSTools getPhotoPersistence(){
        return getPhotoPersistence(MainActivity.DB_MODE);
    }

    // really only for use by Recipe.java to add/remove references
    public static synchronized PhotoList getPhotoList(){
        if(photoList == null){
            photoList = new PhotoList();
        }
        return photoList;
    }

}
