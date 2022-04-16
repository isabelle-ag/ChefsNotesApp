package comp3350.chefsnotes.utils;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

import comp3350.chefsnotes.application.Main;

public class TestUtils {
    private static final File DB_SRC = new File("/data/data/comp3350.chefsnotes/app_db/CNDB.script");

    public static File copyDB() throws IOException {

        final File target = File.createTempFile("temp-db", ".script");
        Files.copy(DB_SRC, target);
        Main.setDBPathName(target.getAbsolutePath().replace(".script", ""));
        return target;
    }

}
