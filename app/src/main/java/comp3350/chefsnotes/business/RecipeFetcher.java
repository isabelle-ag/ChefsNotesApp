package comp3350.chefsnotes.business;

import android.util.Log;

import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;

import comp3350.chefsnotes.application.Services;
import comp3350.chefsnotes.persistence.DBMSTools;
import comp3350.chefsnotes.objects.Recipe;

public class RecipeFetcher implements IRecipeFetcher{
    private final DBMSTools db;

    public RecipeFetcher(DBMSTools db)
    {
        this.db = db;
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
        for (int i=0;i<included.length;i++){
            included[i] = WordUtils.capitalizeFully(included[i]);
            System.out.println(included[i]);
        }
        for (int i=0;i<excluded.length;i++){
            excluded[i] = WordUtils.capitalizeFully(excluded[i]);
        }
        ArrayList<Recipe> out = new ArrayList<Recipe>();

        for (Recipe r: searchSpace)
        {
            boolean good = true;
            for (String inclTag:included)
                if (!r.hasTag(inclTag))
                    good = false;
            for (String exclTag:excluded)
                if (r.hasTag(exclTag))
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
    
    public Recipe[] getRecipesByIngredient(String ing)
    {
        String[] ingArray = ing.trim().split("\\s*;\\s*");
        int ingSize = ingArray.length;
        int fakeItems = 0;
        for(String item : ingArray) {
            if (item.replaceAll(" ","").isEmpty()) {
                fakeItems++;
            }
        }
        if(fakeItems == ingSize){
            return getRecipesByText("");
        }
        ArrayList<Recipe> out = new ArrayList<Recipe>();
        Recipe[] recipes = db.getAllRecipes();
        for (Recipe r:recipes)
        {
            String[] ingNames = r.ingredientList();
            int matches = 0;
            for (String ingredient : ingArray) {
                for (String name: ingNames) {
                    if (name.trim().equalsIgnoreCase(ingredient)) {
                        matches++;
                        break;
                    }
                }
                if(matches == (ingSize-fakeItems)){
                    out.add(r);
                }
            }
        }

        return out.toArray(new Recipe[0]);
    }

}
