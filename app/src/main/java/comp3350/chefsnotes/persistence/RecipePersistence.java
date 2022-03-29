package comp3350.chefsnotes.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import comp3350.chefsnotes.objects.Recipe;
import comp3350.chefsnotes.objects.RecipeExistenceException;

public class RecipePersistence implements DBMSTools{

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
        Recipe[] result = null;

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
        String[] result = null;

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

    @Override
    public Recipe getRecipe(String recipeName) {
        Recipe result = null;

        try (final Connection c = connection()) {   // establish conexion
            // create statement and query
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery(getObjQry);
            // check if there was a result
            if (rs.next()) {
                result = objFromResultSet(rs);
            }
            rs.close();
            st.close();

        } catch (final SQLException e) {
            System.out.println("Unable to get the Recipe.");
        }

        return result;
    }

    @Override // IMPLEMENT
    public String[] searchRecipeNames(String partial) {
        return new String[0];
    }

    @Override
    public boolean createRecipe(String recipeName) /* throws RecipeExistenceException */ {
        boolean result = false;
        Recipe testOut = getRecipe(recipeName);
        if(testOut == null){    // ensure Recipe does not exist
            try (final Connection c = connection()) {   // establish conexion
                // prepare statement
                final PreparedStatement pst = c.prepareStatement(createQry);
                pst.setString(1,recipeName);               // add name
                pst.setObject(2, new Recipe(recipeName));  // initialize with empty object

                int attempt = pst.executeUpdate();  // execute query

                pst.close();

                // verify success
                testOut = getRecipe(recipeName);
                if(attempt>0 && testOut != null && testOut.getTitle().equals(recipeName)){
                    result = true;
                }

            } catch (final SQLException e) {
                System.out.println("Unable to create the Recipe.");
            }
        }
        return result;
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
