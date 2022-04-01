package comp3350.chefsnotes;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.chefsnotes.business.RecipeStuffIT;
import comp3350.chefsnotes.business.TagStuffIT;
import comp3350.chefsnotes.business._BusinessTestSuite;
import comp3350.chefsnotes.objects._ObjectsTestSuite;
import comp3350.chefsnotes.persistence._PersistenceTestSuite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        RecipeStuffIT.class,
        TagStuffIT.class
})

public class IntegrationTests {
}