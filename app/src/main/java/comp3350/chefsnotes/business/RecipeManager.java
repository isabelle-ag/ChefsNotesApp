package comp3350.chefsnotes.business;

import java.util.ArrayList;

import comp3350.chefsnotes.objects.Direction;
import comp3350.chefsnotes.objects.Ingredient;
import comp3350.chefsnotes.objects.Recipe;
import comp3350.chefsnotes.objects.RecipeExistenceException;
import comp3350.chefsnotes.persistence.DBMSTools;

public class RecipeManager implements IRecipeManager {
    private DBMSTools db;

    public RecipeManager(DBMSTools db) {
        this.db = db;
    }

    public Recipe newRecipe(String name) throws RecipeExistenceException
    {
        if(db.getRecipe(name) != null)
        {
            throw new RecipeExistenceException("recipe names must be unique, and '%s' already exists".format(name));
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
            throw new RecipeExistenceException("recipe '%s' does not exist".format(name));
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
            throw new RecipeExistenceException("recipe '%s' has not been saved yet".format(name));
        }
        if(db.getRecipe(newName) != null)
        {
            throw new RecipeExistenceException("recipe names must be unique, and '%s' already exists".format(newName));
        }
        else
        {
            db.updateRecipeName(R, newName);
        }
    }

    public void saveButton(String name, ArrayList<Ingredient> ingredients, ArrayList<Direction> directions) throws RecipeExistenceException
    {
        Recipe myRecipe = this.newRecipe(name);
        for(Ingredient i:ingredients) {
            myRecipe.addIngredient(i);
        }
        for (Direction d:directions) {
            myRecipe.addDirection(d);
        }
        db.commitChanges(myRecipe);
    }

}