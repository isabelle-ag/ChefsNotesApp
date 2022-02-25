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


    // ===== comp3350.chefsnotes.objects.Ingredient Methods =====

    // returns array of all ingredient NAMES in recipe
    String[] ingredientList(String recipe);

    // returns array of all Ingredients in recipe
    // returns null if none exists
    Ingredient[] getIngredients(String recipe);

    // returns a specific comp3350.chefsnotes.objects.Ingredient
    // returns null if not found
    // might be changed to return array of all whose names match?
    Ingredient getIngredient(String recipe, String ingName);

    // adds the ingredient specified as a decimal. 
    // Fails if amount <= 0. Fails if recipe DNE
    // returns true on success, false on failure
    boolean addIngredient(String recipe, String ingName, double amount, String units);

    // adds the ingredient specified as a fraction. 
    // Fails if amount <= 0. Fails if recipe DNE
    // returns true on success, false on failure
    boolean addIngredient(String recipe, String ingName, int numer, int denom, String units);

    // returns true on success, false on failure
    boolean deleteIngredient(String recipe, String ingName);


    // ===== Directions Methods =====

    // returns array of all directions
    Direction[] getDirections(String recipe);

    // returns single direction via step number
    // returns null if out of bounds
    Direction getDirection(String recipe, int dnum);

    // names the direction "comp3350.chefsnotes.objects.Direction <num>" by default
    // returns new direction's number, -1 on failure
    int addDirection(String recipe, String text);

    // addDirection but with a direction name
    int addDirection(String recipe, String text, String dname);

    // addDirection but with name and time
    int addDirection(String recipe, String text, String dname, int time);

    // reorders Directions. Target direction is moved to the new number,
    // and all other Directions are shifted linearly.
    // 0 < newNum < directionCount
    // returns false if the comp3350.chefsnotes.objects.Direction DNE or the number is out of bounds
    boolean moveDirection(String recipe, int dnum, int newNum);

    // deletes the comp3350.chefsnotes.objects.Direction numbered dnum
    // returns true on success, false if DNE
    boolean deleteDirection(String recipe, int dnum);



    // ===== Notes Methods =====



    // ===== Photo Methods =====




}















