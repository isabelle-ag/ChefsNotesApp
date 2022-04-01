package comp3350.chefsnotes.persistence;

import comp3350.chefsnotes.objects.Recipe;
import java.util.ArrayList;

public class FakeDBMS implements DBMSTools{

    private ArrayList<Recipe> recipes;
    private String recent;

    public FakeDBMS(){
        this.recipes = new ArrayList<Recipe>();
        this.recent = "";
    }

    // creates Recipe with name recipeName
    // fails if that name is taken
    // returns true on success, false on failure
    public boolean createRecipe(String recipeName) /* throws RecipeExistenceException */ {
        boolean result = false;

        if(recipeName != null && getRecipe(recipeName) == null){
            result = recipes.add(new Recipe(recipeName));
            this.recent = recipeName;
        }

        return result;
    }

    // private helper method to add Recipe objects directly
    // fails if recipe name already exists
    // returns true on success, false on failure
    private boolean _addRecipe(Recipe newRecipe){
        boolean result = false;

        if(newRecipe != null && getRecipe(newRecipe.getTitle()) == null){
            result = recipes.add(newRecipe);
            this.recent = newRecipe.getTitle();
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

        if(recipeName != null && recipes.size() > 0){
            for( int i=0; i<recipes.size(); i++ ){
                Recipe curr = recipes.get(i);
                if(curr.getTitle().equals(recipeName)){
                    result = curr;
                    recent = recipeName;
                }
            }
        }

        return result;
    }


    // returns full array of every recipe object
    // returns array of size 
    public Recipe[] getAllRecipes(){
        Recipe[] result = new Recipe[recipes.size()];
        int i=0;
        
        for( Recipe curr : recipes ){
            result[i] = curr;
            i++;
        }

        return result;
    }


    // returns array of each Recipe name that contains the partial String passed to it
    // case-insensitive
    // returns empty array if no matches
    public String[] searchRecipeNames(String partial){
        ArrayList<String> searches = new ArrayList<String>();

        if(partial != null) {
            for (String name : getRecipeNames()) {
                if (name.toLowerCase().contains(partial.toLowerCase())) {
                    searches.add(name);
                }
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
        Recipe removeTest;
        Recipe target;

        if(recipeName != null) {
            target = getRecipe(recipeName);

            if (target != null) {
                int location = recipes.indexOf(target);
                removeTest = recipes.remove(location);
                if (removeTest != null) {
                    result = true;
                    if(this.recent.equals(recipeName)){
                        this.recent = null;
                    }
                }
            }
        }

        return result;
    }
    
    // updates Recipe with name recipe to newName
    // fails if recipe DNE or newName already taken
    // returns true on success, false on failure
    public boolean updateRecipeName(String recipe, String newName){
        boolean result = false;
        Recipe target;
        Recipe test;

        if(recipe != null) {
            if (recipe.equals(newName)) { // since no change but no need to fail
                result = true;
                this.recent = newName;

            } else if (newName != null) {
                target = getRecipe(recipe);  // should exist
                test = getRecipe(newName);       // should not exist

                if (target != null && test == null) {
                    target._setTitle(newName);
                    result = true;
                    this.recent = newName;
                }
            }
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
        Recipe target;

        if(modified != null) {
            target = getRecipe(modified.getTitle()); // should already exist

            if (target != null) {
                boolean tryDelete = deleteRecipe(modified.getTitle()); // delete old version
                System.out.println(tryDelete);
                if (tryDelete) {
                    result = _addRecipe(modified);              // save new version
                }
            }
        }

        if(result){
            this.recent = modified.getTitle();
        }

        return result;
    }

    public String duplicateRecipe(String recipe, String newName){
        String result = null;

        Recipe oldOne = getRecipe(recipe);
        Recipe newOne;
        if(oldOne != null){    // ensure Recipe exists
            if(newName!=null){
                if(getRecipe(newName) == null) {
                    newOne = oldOne.duplicateRecipe(newName);
                }
                else {
                    String name = newName+"-copy";
                    while(getRecipe(name) != null){
                        name+="-copy";
                    }
                    newName = this.duplicateRecipe(recipe, name);
                    newOne = oldOne.duplicateRecipe(newName);
                }
            }
            else{
                String name = oldOne.getTitle()+"-copy";
                while(getRecipe(name) != null){
                    name+="-copy";
                }
                newOne = oldOne.duplicateRecipe(name);
            }
            this._addRecipe(newOne);
            result = newOne.getTitle();
        }


        return result;
    }

    public String lastModified(){
        return this.recent;
    }


}








