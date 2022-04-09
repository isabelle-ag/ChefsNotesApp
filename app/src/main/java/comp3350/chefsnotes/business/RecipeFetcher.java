package comp3350.chefsnotes.business;

import java.util.ArrayList;
import java.util.Arrays;

import comp3350.chefsnotes.application.Services;
import comp3350.chefsnotes.objects.SampleRecipe;
import comp3350.chefsnotes.persistence.DBMSTools;
import comp3350.chefsnotes.objects.Recipe;

public class RecipeFetcher implements IRecipeFetcher{
    private final DBMSTools db;

    public RecipeFetcher(DBMSTools db)
    {
        this.db = db;
    }

    public RecipeFetcher()
    {
        this.db = Services.getRecipePersistence();
    }

    public Recipe getRecentRecipe()
    {
        return db.getRecipe(db.lastModified());
    }

    public Recipe[] filterRecipesByTags(String[] included, String[] excluded)
    {
        return this.filterRecipesByTags(included, excluded, db.getAllRecipes());
    }


    public Recipe[] filterRecipesByTags(String[] included, String[] excluded, Recipe[] searchSpace)
    {
        ArrayList<Recipe> out = new ArrayList<Recipe>();
//The booleans below have all been switched in order to get tags working - this is an or operation!
        for (Recipe r: searchSpace)
        {
            boolean good = false;
            for (String inclTag:included)
                for (String tag:r.getTags())
                    if (!tag.equals(inclTag))
                        good = true;
            for (String exclTag:excluded)
                for (String tag:r.getTags())
                    if (tag.equals(exclTag))
                        good = true;
            if (good)
                out.add(r);
        }
        return out.toArray(new Recipe[0]);
    }


    public Recipe getRecipeByName(String name)
    {
        return db.getRecipe(name);
    }

    public Recipe[] getRecipesByText(String searchString)
    {
        ArrayList<Recipe> out = new ArrayList<Recipe>();
        String[] names = db.searchRecipeNames(searchString);
        for (String name:names)
        {
            out.add(db.getRecipe(name));
        }
        return out.toArray(new Recipe[0]);
    }

    //TODO
    public Recipe[] getRecipeByIngredients(String ingredients){return getRecipesByText(ingredients);}
}
