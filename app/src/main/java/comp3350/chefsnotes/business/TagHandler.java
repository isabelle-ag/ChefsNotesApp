package comp3350.chefsnotes.business;

import org.apache.commons.lang3.text.WordUtils;

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
        String tagName = WordUtils.capitalizeFully(tag);
        r.addTag(tagName);
        db.commitChanges(r);
        createTag(tag);
    }

    @Override
    public void removeTagFromRecipe(Recipe r, String tag){
        String tagName = WordUtils.capitalizeFully(tag);
        r.removeTag(tagName);
        db.commitChanges(r);
    }
    
    @Override
    public String[] fetchTags() {
        return tdb.tagList();
    }

    public boolean createTag(String tag)
    {
        String tagName = WordUtils.capitalizeFully(tag);
        try {
            tdb.addTag(tagName);
            return true;
        }
        catch(TagExistenceException e)
        {return false;}
    }

    public boolean deleteTag(String tag) throws TagExistenceException
    {
        String tagName = WordUtils.capitalizeFully(tag);
        boolean success = false;
        String[] inc = {tagName};
        String[] ex = new String[0];
        IRecipeFetcher fetcher = new RecipeFetcher(Services.getRecipePersistence());
        Recipe[] recipes = fetcher.filterRecipesByTags(inc, ex, fetcher.getRecipesByText(""));
        if(recipes.length == 0) {
            tdb.removeTag(tagName);
            success = true;
        }
        return success;
    }
}
