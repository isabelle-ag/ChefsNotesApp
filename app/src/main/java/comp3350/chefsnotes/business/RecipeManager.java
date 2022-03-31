package comp3350.chefsnotes.business;

import java.util.ArrayList;

import comp3350.chefsnotes.application.Services;
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

    public RecipeManager() {
        this.db = Services.getRecipePersistence();
    }

    public Recipe newRecipe(String name) throws RecipeExistenceException
    {
        if(db.getRecipe(name) != null)
        {
            //throw new RecipeExistenceException("recipe names must be unique, and '%s' already exists".format(name));
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

    public Recipe saveButton(String name, ArrayList<Ingredient> ingredients, ArrayList<Direction> directions) throws RecipeExistenceException
    {
        if(db.getRecipe(name) == null) {
            this.newRecipe(name);
        }
        Recipe myRecipe = db.getRecipe(name);


        myRecipe.clearIngredients();
        for(Ingredient i:ingredients) {
            myRecipe.addIngredient(i);
        }
        myRecipe.clearDirections();
        for (Direction d:directions) {
            myRecipe.addDirection(d);
        }
        System.out.println(myRecipe.ingredientList().toString());
        db.commitChanges(myRecipe);
        return myRecipe;
    }

}
