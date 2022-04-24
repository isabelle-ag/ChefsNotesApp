package comp3350.chefsnotes.application;

import static org.junit.Assert.*;

import org.junit.Test;

public class MainTest {
    @Test
    public void testDBMethods(){
        String dbt = "foobar";
        Main.setDBPathName(dbt);
        assertEquals(dbt,Main.getDBPathName());
    }
}
