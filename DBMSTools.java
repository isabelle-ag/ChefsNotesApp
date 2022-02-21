public interface DBMSTools{
    // Any DBMS implementing this interface are required to implement
    // a copy of the methods listed below.

    // ===== Recipe Methods =====

    // UNSURE: will be either all recipes as full json,
    //         or all recipe jsons as the browser cue cards
    String getAllRecipes();

    // returns a comma-separated list of all recipe names
    String getRecipeNames();

    // returns full json of recipe info
    String getRecipe(String recipe);

    // returns list of recipes with names that contain <partial> in them
    // Will either be a list of names or full jsons (probably just csv list)
    String searchRecipe(String partial);

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
    String duplicateRecipe(String recipe, String newName);


    // ===== Ingredient Methods =====

    // returns comma-seperated list of all ingredients in recipe
    String ingredientList(String recipe);

    // returns json format of full ingredient list with Quantities
    // returns null if none exists
    String getIngredients(String recipe);

    // returns json format of a specific ingredient and its Quantity
    // returns null if not found
    String getIngredient(String recipe, String ingName);

    // adds the ingredient specified. If the ingredient already exists,
    // the new amount is added to the old amount. Fails if units mismatch.
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

    // returns full json of all directions
    String getDirections(String recipe);

    // returns json of single direction # dnum
    String getDirection(String recipe, int dnum);

    // returns json of single direction with title dname
    String getDirection(String recipe, String dname);

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















