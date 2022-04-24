package comp3350.chefsnotes.business;

import android.util.Log;

import java.util.ArrayList;

import comp3350.chefsnotes.application.Services;
import comp3350.chefsnotes.objects.Direction;
import comp3350.chefsnotes.objects.Ingredient;
import comp3350.chefsnotes.objects.Recipe;
import comp3350.chefsnotes.objects.RecipeExistenceException;
import comp3350.chefsnotes.persistence.DBMSTools;

public class RecipeManager implements IRecipeManager {
    private final DBMSTools db;

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
            throw new RecipeExistenceException(String.format(name));
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
            throw new RecipeExistenceException(String.format(name));
        }
        if(db.getRecipe(newName) != null)
        {
            throw new RecipeExistenceException(String.format(newName));
        }
        else
        {
            db.updateRecipeName(R, newName);
        }
    }

    public Recipe saveButton(String name, ArrayList<Ingredient> ingredients, ArrayList<Direction> directions, boolean isNew) throws RecipeExistenceException
    {
        if(db.getRecipe(name) == null || isNew) {
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
        db.commitChanges(myRecipe);
        return myRecipe;
    }



    public Recipe copyRecipe(Recipe R, String copyName)
    {
        String oldName = R.getTitle();
        String newName = db.duplicateRecipe(oldName, copyName);
        return db.getRecipe(newName);
    }

    public void updateNotes(Recipe R, String notes)
    {
        R.setNotes(notes);
        db.commitChanges(R);
    }

    public String loadNotes(Recipe R)
    {
        return R.getNotes();
    }

    public void addPhoto(Recipe R, String pathname){
        R.addPhoto(pathname);
        db.commitChanges(R);}

    public String[] getPhotos(Recipe R){
        String[] photos = R.getPhotos();
        if(photos.length >0) {
            for(String s : photos) {
                Log.e("PHOTOS", "getPhotos: photostring: " + s);
            }
        }
        else{
            Log.e("PHOTOS", "getPhotos:empty ");
        }
        return photos;
    }
}
