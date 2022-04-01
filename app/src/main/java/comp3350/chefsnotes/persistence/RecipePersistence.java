package comp3350.chefsnotes.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale;

import comp3350.chefsnotes.objects.Recipe;
import comp3350.chefsnotes.objects.RecipeExistenceException;

public class RecipePersistence implements DBMSTools{

    private String recent;

    private final String dbPath;    // location of db
    private final String nameCol = "RECIPENAME";
    private final String objCol = "RECIPEOBJECT";

    private final String createQry = "INSERT INTO RECIPES (RECIPENAME, RECIPEOBJECT) VALUES (?, ?);";
    private final String deleteQry = "DELETE FROM RECIPES WHERE RECIPENAME = ?;";
    private final String allNameQry = "SELECT RECIPENAME FROM RECIPES WHERE 1=1;";
    private final String allObjQry = "SELECT RECIPEOBJECT FROM RECIPES WHERE 1=1;";
    private final String getObjQry = "SELECT RECIPEOBJECT FROM RECIPES WHERE RECIPENAME = ?;";
    private final String srchNameQry = "SELECT RECIPENAME FROM RECIPES WHERE UPPER(RECIPENAME) LIKE UPPER('%' || ? || '%');";
    private final String updateNameQry = "UPDATE RECIPES SET RECIPENAME = ? WHERE RECIPENAME = ?;";
    private final String commitQry = "UPDATE RECIPES SET RECIPEOBJECT = ? WHERE RECIPENAME = ?;";

    public RecipePersistence(final String dbp){
        this.dbPath = dbp;
        this.recent = "";
        try{
            final Connection con = connection();
        } catch (SQLException sqe) {
            System.out.println("A problem occurred establishing the connection to the RECIPES table.");
        }

    }

    private Connection connection() throws SQLException{
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    @Override
    public Recipe[] getAllRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        Recipe[] result = new Recipe[0];

        try (final Connection c = connection()) {   // establish conexion
            // create statement and query
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery(allObjQry);
            while (rs.next()) {
                Recipe recipe = objFromResultSet(rs);
                recipes.add(recipe);
            }
            rs.close();
            st.close();

            result = recipes.toArray(new Recipe[0]);
        } catch (final SQLException e) {
            System.out.println("Unable to get all Recipes.");
        }

        return result;
    }

    @Override
    public String[] getRecipeNames() {
        ArrayList<String> recipes = new ArrayList<String>();
        String[] result = new String[0];

        try (final Connection c = connection()) {   // establish conexion
            // create statement and query
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery(allNameQry);
            while (rs.next()) {
                final String recipe = nameFromResultSet(rs);
                recipes.add(recipe);
            }
            rs.close();
            st.close();

            result = recipes.toArray(new String[0]);
        } catch (final SQLException e) {
            System.out.println("Unable to get all Recipe names.");
        }

        return result;
    }

    // great for checking if a Recipe exists in the db
    @Override
    public Recipe getRecipe(String recipeName) {
        Recipe result = null;

        try (final Connection c = connection()) {   // establish conexion
            // create statement and query
            final PreparedStatement pst = c.prepareStatement(getObjQry);
            pst.setString(1, recipeName); // add name
            final ResultSet rs = pst.executeQuery();  // execute query

            pst.close();
            // check if there was a result
            if (rs.next()) {
                result = objFromResultSet(rs);
            }
            rs.close();

        } catch (final SQLException e) {
            System.out.println("Unable to get the Recipe.");
            System.out.println(e);
        }

        return result;
    }

    @Override
    public String[] searchRecipeNames(String partial) {
        String[] allNames = getRecipeNames();
        String[] result = new String[0];
        ArrayList<String> builder = new ArrayList<String>();

        for(String name : allNames){
            if(name.toLowerCase().contains(partial.toLowerCase())){
                builder.add(name);
            }
        }

        result = builder.toArray(new String[0]);

        return result;
    }

    @Override
    public boolean createRecipe(String recipeName) /* throws RecipeExistenceException */ {
        return insertRecipe(recipeName, new Recipe(recipeName));
    }

    // private helper for storing a whole recipe object
    private boolean insertRecipe(String recipeName, Recipe recipeObj){
        boolean result = false;
        Recipe testOut = getRecipe(recipeName);
        if(testOut == null){    // ensure Recipe does not exist
            try (final Connection c = connection()) {   // establish conexion
                // prepare statement
                final PreparedStatement pst = c.prepareStatement(createQry);
                pst.setString(1, recipeName); // add name
                pst.setObject(2, recipeObj);  // initialize with empty object

                int attempt = pst.executeUpdate();  // execute query

                pst.close();

                // verify success
                testOut = getRecipe(recipeName);
                if(attempt>0 && testOut != null && testOut.getTitle().equals(recipeName)){
                    result = true;
                    this.recent = recipeName;
                }

            } catch (final SQLException e) {
                System.out.println(e);
                System.out.println("Unable to insert the Recipe.");
            }
        }
        return result;
    }

    @Override
    public boolean deleteRecipe(String recipeName) {
        boolean result = false;
        Recipe testOut = getRecipe(recipeName);
        if(testOut != null){    // ensure Recipe exists
            try (final Connection c = connection()) {   // establish conexion
                // prepare statement
                final PreparedStatement pst = c.prepareStatement(deleteQry);
                pst.setString(1, recipeName); // set name to target

                int attempt = pst.executeUpdate();  // execute query

                pst.close();

                // verify success
                testOut = getRecipe(recipeName);
                if(attempt>0 && testOut == null){
                    result = true;
                }

            } catch (final SQLException e) {
                System.out.println("Unable to delete the Recipe.");
            }
        }
        return result;
    }

    @Override
    public boolean updateRecipeName(String recipe, String newName) {
        boolean result = false;
        Recipe testOut = getRecipe(recipe);
        if(testOut != null){    // ensure Recipe exists
            try (final Connection c = connection()) {   // establish conexion
                // prepare statement
                final PreparedStatement pst = c.prepareStatement(updateNameQry);
                pst.setString(1, newName); // set new name
                pst.setString(2, recipe);  // search for old name

                int attempt = pst.executeUpdate();  // execute query

                pst.close();

                // verify success
                testOut = getRecipe(newName);
                if(attempt>0 && testOut != null && testOut.getTitle().equals(newName)){
                    result = true;
                    this.recent = newName;
                }

            } catch (final SQLException e) {
                System.out.println("Unable to update the Recipe name.");
            }
        }
        return result;
    }

    @Override
    public boolean updateRecipeName(Recipe recipe, String newName) {
        return updateRecipeName(recipe.getTitle(), newName);
    }

    @Override
    public boolean commitChanges(Recipe modified) {
        boolean result = false;
        Recipe testOut = getRecipe(modified.getTitle());
        if(testOut != null){
            boolean testDelete = deleteRecipe(modified.getTitle());

            if(testDelete && insertRecipe(modified.getTitle(), modified) ){ // bias prevents insert
                result = true;
                this.recent = modified.getTitle();
            }
        }
        return result;
    }

    @Override
    public String duplicateRecipe(String recipe, String newName) {
        String result = "";

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
            insertRecipe(newOne.getTitle(), newOne); // insert with full object
            result = newOne.getTitle(); // document return name
        }

        return result;
    }

    public String lastModified(){
        return this.recent;
    }

    private String nameFromResultSet(ResultSet rs) throws SQLException {
        return rs.getString(nameCol);
    }

    private Recipe objFromResultSet(ResultSet rs) throws SQLException {
        Object out = rs.getObject(objCol);
        Recipe result = null;
        if(out instanceof Recipe){
            result = (Recipe) out;
        }
        return result;
    }
}
