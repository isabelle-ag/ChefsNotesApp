package comp3350.chefsnotes.business;

import comp3350.chefsnotes.objects.Recipe;
import comp3350.chefsnotes.objects.TagExistenceException;
import comp3350.chefsnotes.persistence.TagDBMSTools;

public class TagHandler implements ITagHandler{

    private TagDBMSTools tdb;

    public TagHandler(TagDBMSTools tdb)
    {
        this.tdb = tdb;
    }

    @Override
    public void addTagToRecipe(Recipe r, String tag) {
        r.addTag(tag);
        try {
            tdb.addTag(tag);
        }
        catch(TagExistenceException e)
        {};
    }

    @Override
    public void removeTagFromRecipe(Recipe r, String tag) throws TagExistenceException {
        r.removeTag(tag);
        tdb.removeTag(tag);

    }

    @Override
    public String[] fetchTags() {
        return tdb.tagList();
    }
}
