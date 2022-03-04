package comp3350.chefsnotes.business;

import comp3350.chefsnotes.objects.OtherRecipeExample;
import comp3350.chefsnotes.persistence.DBMSTools;
import comp3350.chefsnotes.objects.Recipe;
import comp3350.chefsnotes.objects.OtherRecipeExample;

public class RecipeFetcher implements IRecipeFetcher{
    private final DBMSTools db;

    public RecipeFetcher(DBMSTools db)
    {
        this.db = db;
    }

    public Recipe getRecentRecipe()
    {
        Recipe[] allRecipes = db.getAllRecipes();
        if(allRecipes.length < 1)
        {
            return null;
        }
        return allRecipes[(allRecipes.length-1)];
    }

    public Recipe getRecentRecipe(String s){
        Recipe recipe = new OtherRecipeExample();
        return recipe;
    }

    public Recipe getRecipeByName(String name)
    {
        return db.getRecipe(name);
    }

}
