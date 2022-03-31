package comp3350.chefsnotes.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import comp3350.chefsnotes.objects.TagExistenceException;

public class TagPersistence implements TagDBMSTools{

    private final String dbPath;    // location of db
    private final String nameCol = "TAGNAME";

    private final String addQry = "INSERT INTO TAGS (TAGNAME) VALUES (?);";
    private final String delQry = "DELETE FROM TAGS WHERE TAGNAME = ?;";
    private final String getListQry = "SELECT TAGNAME FROM TAGS WHERE 1=1;";

    public TagPersistence(String dbp){
        this.dbPath = dbp;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    @Override
    public boolean addTag(String tagname) throws TagExistenceException {
        boolean result = false;

        if(!Arrays.asList(tagList()).contains(tagname)){
            try (final Connection c = connection()) {   // establish conexion
                // prepare statement
                final PreparedStatement pst = c.prepareStatement(addQry);
                pst.setString(1, tagname); // add name

                int attempt = pst.executeUpdate();  // execute query

                pst.close();

                // verify success
                if(attempt>0 && Arrays.asList(tagList()).contains(tagname)){
                    result = true;
                }

            } catch (final SQLException e) {
                System.out.println("Unable to insert the Tag.");
            }
        } else {
            throw new TagExistenceException("That tag already exists.");
        }

        return result;
    }

    @Override
    public boolean removeTag(String tagname) throws TagExistenceException {
        boolean result = false;

        if(Arrays.asList(tagList()).contains(tagname)){
            try (final Connection c = connection()) {   // establish conexion
                // prepare statement
                final PreparedStatement pst = c.prepareStatement(delQry);
                pst.setString(1, tagname); // add name

                int attempt = pst.executeUpdate();  // execute query

                pst.close();

                // verify success
                if(attempt>0 && !Arrays.asList(tagList()).contains(tagname)){
                    result = true;
                }

            } catch (final SQLException e) {
                System.out.println("Unable to delete the Tag.");
            }
        } else {
            throw new TagExistenceException("That tag does not exist.");
        }

        return result;
    }

    @Override
    public String[] tagList() {
        ArrayList<String> tags = new ArrayList<String>();
        String[] result = new String[0];

        try (final Connection c = connection()) {   // establish conexion
            // create statement and query
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery(getListQry);
            while (rs.next()) {
                final String tag = nameFromResultSet(rs);
                tags.add(tag);
            }
            rs.close();
            st.close();

            result = tags.toArray(new String[0]);
        } catch (final SQLException e) {
            System.out.println("Unable to get the Tag list.");
        }

        return result;
    }

    private String nameFromResultSet(ResultSet rs) throws SQLException {
        return rs.getString(nameCol);
    }
}
