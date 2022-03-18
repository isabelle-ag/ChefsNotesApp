package comp3350.chefsnotes.persistence;

import comp3350.chefsnotes.objects.Recipe;

public class RecipePersistence implements DBMSTools{
    @Override
    public Recipe[] getAllRecipes() {
        return new Recipe[0];
    }

    @Override // IMPLEMENT
    public String[] getRecipeNames() {
        return new String[0];
    }

    @Override // IMPLEMENT
    public Recipe getRecipe(String recipeName) {
        return null;
    }

    @Override // IMPLEMENT
    public String[] searchRecipeNames(String partial) {
        return new String[0];
    }

    @Override // IMPLEMENT
    public boolean createRecipe(String recipeName) {
        return false;
    }

    @Override // IMPLEMENT
    public boolean deleteRecipe(String recipeName) {
        return false;
    }

    @Override // IMPLEMENT
    public boolean updateRecipeName(String recipe, String newName) {
        return false;
    }

    @Override // IMPLEMENT
    public boolean updateRecipeName(Recipe recipe, String newName) {
        return false;
    }

    @Override // IMPLEMENT
    public boolean commitChanges(Recipe modified) {
        return false;
    }

    @Override // IMPLEMENT
    public String duplicateRecipe(String recipe, String newName) {
        return null;
    }
}
