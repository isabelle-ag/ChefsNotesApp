package comp3350.chefsnotes.business;

import comp3350.chefsnotes.objects.Recipe;

public interface IRecipeFetcher {

    public Recipe getRecentRecipe();
    public Recipe getRecipeByName(String name);

}
