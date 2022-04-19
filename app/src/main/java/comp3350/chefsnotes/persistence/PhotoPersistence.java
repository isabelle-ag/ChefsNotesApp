package comp3350.chefsnotes.persistence;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import comp3350.chefsnotes.objects.Photo;

public class PhotoPersistence implements PhotoDBMSTools{

    private String dbPath;

    private final String photoCol = "PATHNAME";
    private final String refCol = "REFCOUNT";
    private final String getAllQry = "SELECT * FROM PHOTOS WHERE 1=1;";
    private final String getPhotoQry = "SELECT * FROM PHOTOS WHERE PATHNAME = ?;";
    private final String addPhotoQry = "INSERT INTO PHOTOS (PATHNAME, REFCOUNT) VALUES (?, ?);";
    private final String remPhotoQry = "DELETE FROM PHOTOS WHERE PATHNAME = ?;";
    private final String updateRefQry = "UPDATE PHOTOS SET REFCOUNT = ? WHERE PATHNAME = ?;";


    public PhotoPersistence(String dbp){
        Logger.getLogger("hsqldb.db").setLevel(Level.WARNING);
        this.dbPath = dbp;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";ifexists=true;shutdown=true", "SA", "");
    }

    @Override
    public Photo[] getAllPhotos() {
        ArrayList<Photo> photos = new ArrayList<Photo>();
        Photo[] result = new Photo[0];

        try (final Connection c = connection()) {   // establish conexion
            // create statement and query
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery(getAllQry);
            while (rs.next()) {
                Photo photo = photoFromResultSet(rs);
                photos.add(photo);
            }
            rs.close();
            st.close();

            result = photos.toArray(new Photo[0]);
        } catch (final SQLException e) {
            System.out.println("Unable to get all Photos.");
        }

        return result;
    }

    @Override
    public boolean addPhoto(String pathname) {
        boolean result = false;
        Photo testOut = getPhoto(pathname);
        if(testOut == null){    // ensure Photo does not exist
            try (final Connection c = connection()) {   // establish conexion
                // prepare statement
                final PreparedStatement pst = c.prepareStatement(addPhotoQry);
                pst.setString(1, pathname); // add name
                pst.setObject(2, 1);  // initialize with one reference

                int attempt = pst.executeUpdate();  // execute query

                pst.close();

                // verify success
                testOut = getPhoto(pathname);
                if(attempt>0 && testOut != null && testOut.getPathname().equals(pathname)){
                    result = true;
                }

            } catch (final SQLException e) {
                System.out.println(e);
                System.out.println("Unable to insert the Photo.");
            }
        }
        return result;
    }

    @Override
    public boolean removePhoto(String pathname) {
        boolean result = false;
        Photo testOut = getPhoto(pathname);
        if(testOut != null){    // ensure Recipe exists
            try (final Connection c = connection()) {   // establish conexion
                // prepare statement
                final PreparedStatement pst = c.prepareStatement(remPhotoQry);
                pst.setString(1, pathname); // set name to target

                int attempt = pst.executeUpdate();  // execute query

                pst.close();

                // verify success
                testOut = getPhoto(pathname);
                if(attempt>0 && testOut == null){
                    result = true;
                }

            } catch (final SQLException e) {
                System.out.println("Unable to delete the Photo.");
            }
        }

        if(result){ // successful removal needs deletion
            new File(pathname).delete();
        }

        return result;
    }

    @Override
    public boolean setReference(String pathname, int ref) {
        boolean result = false;
        Photo testOut = getPhoto(pathname);
        if(testOut != null){    // ensure Photo exists
            try (final Connection c = connection()) {   // establish conexion
                // prepare statement
                final PreparedStatement pst = c.prepareStatement(updateRefQry);
                pst.setInt(1, ref); // set new ref count
                pst.setString(2, pathname);  // set old name

                int attempt = pst.executeUpdate();  // execute query

                pst.close();

                // verify success
                testOut = getPhoto(pathname);
                if(attempt>0 && testOut != null && testOut.getRefCount() == ref){
                    result = true;
                }

            } catch (final SQLException e) {
                System.out.println("Unable to update the Photo count.");
            }
        }
        return result;
    }

    private Photo photoFromResultSet(ResultSet rs){
        Photo result = null;

        try {
            String pathname = rs.getString(photoCol);
            int refCount = rs.getInt(refCol);
            result = new Photo(pathname, refCount);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }

    // private helper to see if the photo exists
    private Photo getPhoto(String pathname) {
        Photo result = null;

        if(pathname != null){
            try (final Connection c = connection()) {   // establish conexion
                // create statement and query
                final PreparedStatement pst = c.prepareStatement(getPhotoQry);
                pst.setString(1, pathname); // add name
                final ResultSet rs = pst.executeQuery();  // execute query

                pst.close();
                // check if there was a result
                if (rs.next()) {
                    result = photoFromResultSet(rs);
                }
                rs.close();

            } catch (final SQLException e) {
                System.out.println("Unable to get the Recipe.");
                System.out.println(e);
            }
        }

        return result;
    }
}
