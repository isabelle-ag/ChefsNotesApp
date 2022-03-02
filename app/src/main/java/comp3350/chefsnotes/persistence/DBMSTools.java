package comp3350.chefsnotes.persistence;

import comp3350.chefsnotes.objects.Direction;
import comp3350.chefsnotes.objects.Ingredient;
import comp3350.chefsnotes.objects.Recipe;

public interface DBMSTools{
    // Any DBMS implementing this interface are required to implement
    // a copy of the methods listed below.

    // ===== comp3350.chefsnotes.objects.Recipe Methods =====

    // returns array of all Recipe object
    Recipe[] getAllRecipes();

    // returns array of all recipe names
    String[] getRecipeNames();

    // returns specified Recipe object with exact-match name
    Recipe getRecipe(String recipeName);

    // returns array of recipes with names that contain <partial> in them
    // returns null if no recipes match
    String[] searchRecipeNames(String partial);

    // creates new recipe with given name
    // fails if name already exists
    // returns true on success, false on failure
    boolean createRecipe(String recipeName);

    // deletes recipe with given name
    // fails if recipe DNE
    // returns true on deletion, false otherwise
    boolean deleteRecipe(String recipeName);
    
    // updates a recipe to have a new name
    // fails if recipe DNE, fails if another recipe has that name
    // returns true on success, false on failure
    boolean updateRecipeName(String recipe, String newName);

    // updates a recipe to have a new name
    // fails if recipe no longer in DB, fails if another recipe has that name
    // returns true on success, false on failure
    boolean updateRecipeName(Recipe recipe, String newName);

    // saves changes made to modified via Recipe mutator methods
    // fails if modified no longer in DB
    // returns true on success, false on failure
    boolean commitChanges(Recipe modified);

    // creates a deep copy of the target recipe, with newName as its title. 
    // if newName = null, default is <oldName> + " - copy" + <copyNum>
    // returns the name of the new recipe
    // returns null if it somehow fails
    String duplicateRecipe(String recipe, String newName);


}















