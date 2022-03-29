package comp3350.chefsnotes.business;

import comp3350.chefsnotes.objects.Recipe;
import comp3350.chefsnotes.objects.TagExistenceException;

public interface ITagHandler {

    public void addTagToRecipe(Recipe r, String tag);

    public void removeTagFromRecipe(Recipe r, String tag) throws TagExistenceException;

    public String[] fetchTags();
}
