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
        //TODO use proper db impl for this
        Recipe[] allRecipes = db.getAllRecipes();
        if(allRecipes.length < 1)
        {
            return null;
        }
        return allRecipes[(allRecipes.length-1)];
    }

    public Recipe[] filterRecipesByTags(String[] included, String[] excluded)
    {
        return this.filterRecipesByTags(included, excluded, db.getAllRecipes());
    }


    public Recipe[] filterRecipesByTags(String[] included, String[] excluded, Recipe[] searchSpace)
    {
        ArrayList<Recipe> out = new ArrayList<Recipe>();

        for (Recipe r: searchSpace)
        {
            boolean good = true;
            for (String inclTag:included)
                for (String tag:r.getTags())
                    if (!tag.equals(inclTag))
                        good = false;
            for (String exclTag:excluded)
                for (String tag:r.getTags())
                    if (tag.equals(exclTag))
                        good = false;
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

    public String[] getRecipeNamesByText(String name){
        return  db.searchRecipeNames(name);
    }

}
