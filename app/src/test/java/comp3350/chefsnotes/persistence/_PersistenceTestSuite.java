package comp3350.chefsnotes.persistence;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.chefsnotes.objects.IngredientTest;
import comp3350.chefsnotes.objects.QuantityNumTest;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        DBMSToolsTest.class,
        PhotoDBMSTools.class//,
        //PhotoListTest.class,
        //TagDBMSToolsTest.class
})


public class _PersistenceTestSuite {
}
