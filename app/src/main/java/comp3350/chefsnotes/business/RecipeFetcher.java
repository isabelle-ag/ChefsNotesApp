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

    public String[] filterRecipeNamesByTags(String[] included, String[] excluded, Recipe[] searchSpace) {
        ArrayList<String> out = new ArrayList<>();

        for (Recipe r: searchSpace)
        {
            //To make tag filtering work, I changed some things, originals are commented
            boolean good = false; //true
            for (String inclTag:included)
                for (String tag:r.getTags())
                    if (tag.equals(inclTag))
                        good = true; //false
            for (String exclTag:excluded)
                for (String tag:r.getTags())
                    if (tag.equals(exclTag))
                        good = true; //false
            if (good)
                out.add(r.getTitle());
        }
        return out.toArray(new String[0]);
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
