package comp3350.chefsnotes.business;

import comp3350.chefsnotes.objects.Direction;
import comp3350.chefsnotes.objects.Ingredient;
import comp3350.chefsnotes.persistence.DBMSTools;
import comp3350.chefsnotes.objects.Recipe;

public class RecipeFetcher implements IRecipeFetcher {
    private final DBMSTools db;
    private Recipe currentRecipe;

    public RecipeFetcher(DBMSTools db) {
        this.db = db;
    }

    public void setRecentRecipe(){
        Recipe[] allRecipes = db.getAllRecipes();
        if(allRecipes.length >= 1)
        {
            currentRecipe = allRecipes[(allRecipes.length-1)];
        }
        return;
    }

    public Recipe getRecentRecipe() {
        setRecentRecipe();
        if(currentRecipe == null){
            return null;
        }
        return currentRecipe;
    }

    public Recipe getRecipeByName(String name)
    {
        return db.getRecipe(name);
    }

    public String[] getIngredients(){
        Ingredient[] ingArray = currentRecipe.getIngredients();
        int n = ingArray.length;
        String[] strArray = new String[n];
        for(int i=0; i<n; i++){
            strArray[i]= ingArray[i].getAmtString() + "\t" + ingArray[i].getName();
        }
        return strArray;
    }

    public String[] getDirections(){
        Direction[] dirArray = currentRecipe.getDirections();
        int n = dirArray.length;
        String[] strArray = new String[n];
        for(int i=0; i<n; i++){
            strArray[i]= "Step " + i + " " + dirArray[i].getName() + "\n" + dirArray[i].getText();
        }
        return strArray;
    }
    public String getRecipeName(){
        return(currentRecipe.getTitle());
    }

    public boolean validRecipe(){
        if(currentRecipe == null){
            return false;
        }
        return true;
    }
}
