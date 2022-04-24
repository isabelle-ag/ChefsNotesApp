package comp3350.chefsnotes;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.chefsnotes.application._ApplicationTestSuite;
import comp3350.chefsnotes.business._BusinessTestSuite;
import comp3350.chefsnotes.objects._ObjectsTestSuite;
import comp3350.chefsnotes.persistence._PersistenceTestSuite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        _ApplicationTestSuite.class,
        _BusinessTestSuite.class,
        _ObjectsTestSuite.class,
        _PersistenceTestSuite.class
})

public class AllTests {
}