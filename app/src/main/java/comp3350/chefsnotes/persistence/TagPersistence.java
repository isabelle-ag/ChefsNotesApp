package comp3350.chefsnotes.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TagPersistence implements TagDBMSTools{

    private final String dbPath;    // location of db

    public TagPersistence(String dbp){
        this.dbPath = dbp;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }
}
