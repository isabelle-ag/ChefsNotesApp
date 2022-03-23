package comp3350.chefsnotes.application;

import comp3350.chefsnotes.persistence.DBMSTools;
import comp3350.chefsnotes.persistence.FakeDBMS;
import comp3350.chefsnotes.persistence.FakeTagDB;
import comp3350.chefsnotes.persistence.RecipePersistence;
import comp3350.chefsnotes.persistence.TagDBMSTools;
import comp3350.chefsnotes.persistence.TagPersistence;

public class Services {
    public static final boolean MODE_FAKE = false;
    public static final boolean MODE_REAL = true;

    private static DBMSTools recipePersistence = null;
    private static TagDBMSTools tagPersistence = null;

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

}
