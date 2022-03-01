package comp3350.chefsnotes.business;

import java.util.ArrayList;

import comp3350.chefsnotes.objects.Direction;
import comp3350.chefsnotes.objects.Ingredient;
import comp3350.chefsnotes.objects.Recipe;
import comp3350.chefsnotes.objects.RecipeExistenceException;
import comp3350.chefsnotes.persistence.DBMSTools;

public class RecipeManager {
    private DBMSTools db;

    RecipeManager(DBMSTools db) {
        this.db = db;
    }

    public Recipe newRecipe(String name) throws RecipeExistenceException
    {
        if(db.getRecipe(name) != null)
        {
            throw new RecipeExistenceException("recipe '%s' already exists in db".format(name));
        }
        else
        {
            db.createRecipe(name);
        }
        return db.getRecipe(name);
    }

    public void delRecipe(Recipe R) throws RecipeExistenceException
    {
        String name = R.getTitle();
        if(db.getRecipe(name) == null)
        {
            throw new RecipeExistenceException("recipe '%s' does not exist in db".format(name));
        }
        else
        {
            db.deleteRecipe(name);
        }
    }

    public void renameRecipe(Recipe R, String newName) throws RecipeExistenceException
    {
        String name = R.getTitle();
        if(db.getRecipe(name) == null)
        {
            throw new RecipeExistenceException("recipe '%s' does not exist in db".format(name));
        }
        if(db.getRecipe(newName) != null)
        {
            throw new RecipeExistenceException("recipe '%s' already exists in db".format(newName));
        }
        else
        {
            db.updateRecipeName(R, newName);
        }
    }

    public void saveButton(String name, ArrayList<Ingredient> ingredients, ArrayList<Direction> directions) throws RecipeExistenceException
    {
        Recipe myRecipe = this.newRecipe(name);
        //TODO
    }

}