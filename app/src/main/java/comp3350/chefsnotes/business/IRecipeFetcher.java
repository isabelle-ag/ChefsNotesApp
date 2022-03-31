package comp3350.chefsnotes.business;

import comp3350.chefsnotes.objects.Recipe;

public interface IRecipeFetcher {

    public Recipe getRecentRecipe();
    public Recipe getRecipeByName(String name);
    public Recipe[] filterRecipesByTags(String[] included, String[] excluded);
    public Recipe[] filterRecipesByTags(String[] included, String[] excluded, Recipe[] searchSpace);

    public Recipe[] getRecipesByText(String name);
    public String[] getRecipeNamesByText(String name);

}
