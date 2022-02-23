public interface DBMSTools{
    // Any DBMS implementing this interface are required to implement
    // a copy of the methods listed below.

    // ===== Recipe Methods =====

    // UNSURE: will be either all recipes as full json,
    //         or all recipe jsons as the browser cue cards,
    //         or array of Recipe objects...
    // might not use.
    String getAllRecipes();

    // returns array of all recipe names
    String[] getRecipeNames();

    // returns full json of recipe info, or Recipe object
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
    boolean updateRecipe(String recipe, String newName);

    // creates a deep copy of the target recipe, with newName as its title. 
    // if newName = null, default is <oldName> + " - copy" + <copyNum>
    // returns the name of the new recipe
    // returns null if it somehow fails
    String duplicateRecipe(String recipe, String newName);


    // ===== Ingredient Methods =====

    // returns array of all ingredient NAMES in recipe
    String[] ingredientList(String recipe);

    // returns array of all Ingredients in recipe
    // returns null if none exists
    Ingredient[] getIngredients(String recipe);

    // returns a specific Ingredient
    // returns null if not found
    // might be changed to return array of all whose names match?
    Ingredient getIngredient(String recipe, String ingName);

    // adds the ingredient specified. 
    // Fails if amount <= 0. Fails if recipe DNE
    // returns true on success, false on failure
    boolean addIngredient(String recipe, String ingName, int amount, String units);

    // updates an existing ingredient's information -- CANNOT CHANGE NAME
    // fails if ingredient DNE
    // returns true on success, false on failure.
    boolean updateIngredient(String recipe, String ingName, int amount, String units);

    // updates an existing ingredient's name to newName
    // fails if ingredient DNE
    boolean updateIngredient(String recipe, String ingName, String newName);

    // returns true on success, false on failure
    boolean deleteIngredient(String recipe, String ingName);


    // ===== Directions Methods =====

    // returns array of all directions
    Direction[] getDirections(String recipe);

    // returns single direction via step number
    // returns null if out of bounds
    Direction getDirection(String recipe, int dnum);

    // returns single direction via title dname
    // returns null if DNE
    Direction getDirection(String recipe, String dname);

    // names the direction "Direction <num>" by default
    // returns new direction's number, -1 on failure
    int addDirection(String recipe, String text);

    // addDirection but with a direction name
    int addDirection(String recipe, String text, String dname);

    // addDirection but with name and time
    int addDirection(String recipe, String text, String dname, int time);

    // sets the direction length
    // returns false if DNE
    boolean setTime(String recipe, int dnum, int time);

    // updates Direction text
    // returns true on success, false if DNE
    boolean updateDirection(String recipe, int dnum, String newText);

    // changes direction name
    // returns false if direction name DNE or newName matches
    // an existing direction name in recipe
    boolean updateDirection(String recipe, String oldName, String newName);

    // reorders Directions. Target direction is moved to the new number,
    // and all other Directions are shifted linearly.
    // 0 < newNum < directionCount
    // returns false if the Direction DNE or the number is out of bounds
    boolean moveDirection(String recipe, int dnum, int newNum);

    // deletes the Direction numbered dnum
    // returns true on success, false if DNE
    boolean deleteDirection(String recipe, int dnum);

    // deletes the Direction with title dname
    // returns true on success, false if DNE
    boolean deleteDirection(String recipe, String dname);



    // ===== Notes Methods =====



    // ===== Photo Methods =====




}















