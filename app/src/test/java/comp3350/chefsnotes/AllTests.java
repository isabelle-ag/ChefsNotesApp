package comp3350.chefsnotes;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.chefsnotes.business.BusinessTestSuite;
import comp3350.chefsnotes.objects.ObjectsTestSuite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        BusinessTestSuite.class,
        ObjectsTestSuite.class
})

public class AllTests {
}