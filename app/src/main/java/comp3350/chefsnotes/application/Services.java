package comp3350.chefsnotes.application;

import comp3350.chefsnotes.persistence.DBMSTools;
import comp3350.chefsnotes.persistence.RecipePersistence;
import comp3350.chefsnotes.persistence.TagDBMSTools;
import comp3350.chefsnotes.persistence.TagPersistence;

public class Services {
    private static DBMSTools recipePersistence;
    private static TagDBMSTools tagPersistence;

    public static synchronized DBMSTools getRecipePersistence(){    // uhh dependency injection
        if(recipePersistence == null){
            recipePersistence = new RecipePersistence(Main.getDBPathName());
        }

        return recipePersistence;
    }

    public static synchronized TagDBMSTools getTagPersistence(){    // brr dependency injection
        if(tagPersistence == null){
            tagPersistence = new TagPersistence(Main.getDBPathName());
        }

        return tagPersistence;
    }
}
