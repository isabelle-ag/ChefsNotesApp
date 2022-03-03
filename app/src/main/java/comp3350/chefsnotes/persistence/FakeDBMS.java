package comp3350.chefsnotes.persistence;

import comp3350.chefsnotes.objects.Recipe;
import java.util.ArrayList;

class FakeDBMS implements DBMSTools{

    ArrayList<Recipe> recipes;

    public FakeDBMS(){
        recipes = new ArrayList<Recipe>();
    }


    // creates Recipe with name recipeName
    // fails if that name is taken
    // returns true on success, false on failure
    public boolean createRecipe(String recipeName){
        boolean result = false;

        if(getRecipe(recipeName) == null){
            result = recipes.add(new Recipe(recipeName));
        }

        return result;
    }

    // private helper method to add Recipe objects directly
    // fails if recipe name already exists
    // returns true on success, false on failure
    private boolean _addRecipe(Recipe newRecipe){
        boolean result = false;

        if(getRecipe(recipeName) == null){
            result = recipes.add(newRecipe);
        }

        return result;
    }


    // returns names of all recipes
    // returns array size 0 if none exist
    public String[] getRecipeNames(){
        String[] result = new String[recipes.size()];
        int i=0;
        
        for( Recipe curr : recipes ){
            result[i] = curr.getTitle();
            i++;
        }

        return result;
    }

    // returns a single recipe with given name if it is an exact match
    // compares to recipeName
    // can be used as a verifier of existence or not
    // returns null if DNE
    public Recipe getRecipe(String recipeName){
        Recipe result = null;

        if(recipes.size() > 0){
            for( int i=0; i<recipes.size(); i++ ){
                Recipe curr = recipes.get(i);
                if(curr.getTitle().equals(target)){
                    result = curr;
                }
            }
        }

        return result;
    }


    // returns full array of every recipe object
    // returns array of size 
    Recipe[] getAllRecipes(){
        Recipe[] result = new Recipe[recipes.size()];
        int i=0;
        
        for( Recipe curr : recipes ){
            result[i] = curr;
            i++;
        }

        return result;
    }


    // returns array of each Recipe name that contains the partial String passed to it
    // returns empty array if no matches
    String[] searchRecipeNames(String partial){
        ArrayList<String> searches = new ArrayList<String>();

        for( Recipe curr : recipes ){
            if(curr.getTitle().contains(partial)){
                searches.add(curr.getTitle());
            }
        }

        String[] result = searches.toArray(new String[0]);

        return result;
    }


    // deletes the Recipe names recipeName
    // fails if recipeName DNE
    // returns true on success, false on failure
    public boolean deleteRecipe(String recipeName){
        boolean result = false;
        Recipe target = getRecipe(recipeName); 

        if(target != null){
            int location = recipes.indexOf(target);
            result = recipes.remove(location);
        }

        return result;
    }
    
    // updates Recipe with name recipe to newName
    // fails if recipe DNE or newName already taken
    // returns true on success, false on failure
    public boolean updateRecipeName(String recipe, String newName){
        boolean result = false;
        Recipe target = getRecipe(recipe);  // should exist
        Recipe test = getRecipe(newName);       // should not exist

        if(target != null && test == null){
            target._setTitle(newName);
            result = true;
        }

        return result;
    }

    // updates Recipe object recipe to newName
    // fails if recipe DNE or newName already taken
    // returns true on success, false on failure
    public boolean updateRecipeName(Recipe recipe, String newName){
        return updateRecipeName(recipe.getTitle(), newName);
    }

    // saves a new version of an edited Recipe
    // fails if the Recipe is not in the DB
    // returns true on success, false on failure
    public boolean commitChanges(Recipe modified){
        boolean result = false;
        Recipe target = getRecipe(modified.getTitle()); // should already exist

        if(target != null){
            boolean tryDelete = deleteRecipe(modified.getTitle()); // delete old version
            if(tryDelete){
                result = _addRecipe(modified);              // save new version
            }
        }

        return result;
    }

    // needs implementation
    public String duplicateRecipe(String recipe, String newName){
        return null;
    }


}








