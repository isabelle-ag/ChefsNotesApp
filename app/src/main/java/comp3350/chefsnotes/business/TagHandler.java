package comp3350.chefsnotes.business;

import comp3350.chefsnotes.application.Services;
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

    public boolean deleteTag(String tag) throws TagExistenceException
    {
        boolean success = false;
        String[] inc = {tag};
        String[] ex = new String[0];
        IRecipeFetcher fetcher = new RecipeFetcher(Services.getRecipePersistence());
        Recipe[] recipes = fetcher.filterRecipesByTags(inc, ex, fetcher.getRecipesByText(""));
        if(recipes.length == 0) {
            tdb.removeTag(tag);
            success = true;
        }
        return success;
    }
}
