package comp3350.chefsnotes.business;

import comp3350.chefsnotes.objects.Recipe;

public interface IRecipeFetcher {

    public Recipe getRecentRecipe();
    public Recipe getRecipeByName(String name);
    public String[] getIngredients();
    public String[] getDirections();
    public String getRecipeName();
    public void setRecentRecipe();
    public boolean validRecipe();
}
