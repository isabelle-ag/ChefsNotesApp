package comp3350.chefsnotes.persistance;

import comp3350.chefsnotes.objects.Direction;
import comp3350.chefsnotes.objects.Ingredient;

public interface DBMSTools{
    // Any DBMS implementing this interface are required to implement
    // a copy of the methods listed below.

    // ===== comp3350.chefsnotes.objects.Recipe Methods =====

    // UNSURE: will be either all recipes as full json,
    //         or all recipe jsons as the browser cue cards,
    //         or array of comp3350.chefsnotes.objects.Recipe objects...
    // might not use.
    String getAllRecipes();

    // returns array of all recipe names
    String[] getRecipeNames();

    // returns full json of recipe info, or comp3350.chefsnotes.objects.Recipe object
    // might not use.
    String getRecipe(String recipe);

    // returns array of recipes with names that contain <partial> in them
    // returns null if no recipes match
    String[] searchRecipe(String partial);

    // creates new recipe with given name
    // fails if name already exists
    // returns true on success, false on failure
    boolean createRecipe(String name);

    // deletes recipe with given name
    // fails if recipe DNE
    // returns true on deletion, false otherwise
    boolean deleteRecipe(String recipe);
    
    // updates a recipe to have a new name
    // fails if recipe DNE
    // returns true on success, false on failure
    boolean updateRecipeName(String recipe, String newName);

    // creates a deep copy of the target recipe, with newName as its title. 
    // if newName = null, default is <oldName> + " - copy" + <copyNum>
    // returns the name of the new recipe
    // returns null if it somehow fails
    String duplicateRecipe(String recipe, String newName);


}















