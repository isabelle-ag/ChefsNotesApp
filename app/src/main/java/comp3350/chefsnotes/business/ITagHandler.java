package comp3350.chefsnotes.business;

import comp3350.chefsnotes.objects.Recipe;
import comp3350.chefsnotes.objects.TagExistenceException;

public interface ITagHandler {

    void addTagToRecipe(Recipe r, String tag);

    void removeTagFromRecipe(Recipe r, String tag);

    String[] fetchTags();

    void createTag(String tag);

    boolean deleteTag(String tag) throws TagExistenceException;

}
