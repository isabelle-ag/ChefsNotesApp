package comp3350.chefsnotes.business;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

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

//    public String[] filterRecipeNamesByTags(String[] included, String[] excluded, Recipe[] searchSpace) {
//        ArrayList<String> out = new ArrayList<>();
//
//        for (Recipe r: searchSpace)
//        {
//            //To make tag filtering work, I changed some things, originals are commented
//            boolean good = false; //true
//            for (String inclTag:included)
//                for (String tag:r.getTags())
//                    if (tag.equals(inclTag))
//                        good = true; //false
//            for (String exclTag:excluded)
//                for (String tag:r.getTags())
//                    if (tag.equals(exclTag))
//                        good = true; //false
//            if (good)
//                out.add(r.getTitle());
//        }
//        return out.toArray(new String[0]);
//    }

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
