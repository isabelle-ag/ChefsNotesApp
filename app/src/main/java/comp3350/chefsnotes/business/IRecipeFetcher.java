package comp3350.chefsnotes.business;

import comp3350.chefsnotes.objects.Recipe;

public interface IRecipeFetcher {

    Recipe getRecentRecipe();
    Recipe getRecipeByName(String name);
    Recipe getLastViewed();
    Recipe[] filterRecipesByTags(String[] included, String[] excluded);
    Recipe[] filterRecipesByTags(String[] included, String[] excluded, Recipe[] searchSpace);
    String[] filterRecipeNamesByTags(String[] included, String[] excluded, Recipe[] searchSpace);
    Recipe[] getRecipesByText(String name);
    String[] getRecipeNamesByText(String name);

}
