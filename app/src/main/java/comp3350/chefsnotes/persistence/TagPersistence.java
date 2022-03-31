package comp3350.chefsnotes.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import comp3350.chefsnotes.objects.TagExistenceException;

public class TagPersistence implements TagDBMSTools{

    private final String dbPath;    // location of db
    private final String addQry = "INSERT INTO TAGS ";

    public TagPersistence(String dbp){
        this.dbPath = dbp;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    @Override
    public boolean addTag(String tagname) throws TagExistenceException {
        return false;
    }

    @Override
    public boolean removeTag(String tagname) throws TagExistenceException {
        return false;
    }

    @Override
    public String[] tagList() {
        return new String[0];
    }
}
