package comp3350.chefsnotes.business;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        IRecipeManagerTest.class,
        IRecipeFetcherTest.class
})

public class BusinessTestSuite {
}