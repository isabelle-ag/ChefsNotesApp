package comp3350.chefsnotes.business;

import comp3350.chefsnotes.objects.Recipe;
import comp3350.chefsnotes.objects.TagExistenceException;
import comp3350.chefsnotes.persistence.DBMSTools;
import comp3350.chefsnotes.persistence.TagDBMSTools;

public class TagHandler implements ITagHandler{

    private TagDBMSTools tdb;
    private DBMSTools db;

    public TagHandler(TagDBMSTools tdb, DBMSTools db)
    {
        this.tdb = tdb;
        this.db = db;
    }

    @Override
    public void addTagToRecipe(Recipe r, String tag) {
        r.addTag(tag);
        db.commitChanges(r);
        createTag(tag);
    }

    @Override
    public void removeTagFromRecipe(Recipe r, String tag){
        r.removeTag(tag);
        db.commitChanges(r);
    }
    
    @Override
    public String[] fetchTags() {
        return tdb.tagList();
    }

    public boolean createTag(String tag)
    {
        try {
            tdb.addTag(tag);
            return true;
        }
        catch(TagExistenceException e)
        {return false;}
    }

    public void deleteTag(String tag) throws TagExistenceException
    {
        tdb.removeTag(tag);
    }
}
