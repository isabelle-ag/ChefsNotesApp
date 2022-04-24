package comp3350.chefsnotes.business;

import java.util.ArrayList;

import comp3350.chefsnotes.objects.Direction;
import comp3350.chefsnotes.objects.Ingredient;
import comp3350.chefsnotes.objects.Recipe;
import comp3350.chefsnotes.objects.RecipeExistenceException;

public interface IRecipeManager {
    Recipe newRecipe(String name) throws RecipeExistenceException;
    void delRecipe(Recipe R) throws RecipeExistenceException;
    void renameRecipe(Recipe R, String newName) throws RecipeExistenceException;
    public Recipe saveButton(String name, ArrayList<Ingredient> ingredients, ArrayList<Direction> directions, boolean isNew) throws RecipeExistenceException;
    public Recipe copyRecipe(Recipe R, String copyName);
    public void updateNotes(Recipe R, String notes);
    public String loadNotes(Recipe R);
    public void addPhoto(Recipe R, String pathname);
    public String[] getPhotos(Recipe R);
}

