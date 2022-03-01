import comp3350.chefsnotes.objects.*;

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


    // returns names of all recipes
    // returns array size 0 if none exist
    public String[] getRecipeNames(){
        String[] result = new String[recipes.size()];
        int i=0;
        
        for( Recipe curr : recipes ){
            result[i] = curr.getName();
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
                if(curr.getName().equals(target)){
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
            if(curr.getName().contains(partial)){
                searches.add(curr.getName());
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

        if(Recipe target = getRecipe(recipeName) != null){
            int location = recipes.indexOf(target);
            result = recipes.remove(location);
        }

        return result;
    }
    


}








