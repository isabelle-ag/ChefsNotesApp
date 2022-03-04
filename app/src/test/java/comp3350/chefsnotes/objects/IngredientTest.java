package comp3350.chefsnotes.objects;

import comp3350.chefsnotes.business.Units;
import comp3350.chefsnotes.objects.Ingredient;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IngredientTest {
    private Ingredient testIngredientDecimal;
    private Ingredient testIngredientFraction;
    private final String decimalName = "Leeks";
    private final String fractionName = "Flour";
    private final double DECAMT = 12.5;
    private final int NUMAMT = 3;
    private final int DENAMT = 4;
    private final static double EPSILON = 0.00001d;

    @Before
    public void initialize(){
        testIngredientDecimal = new Ingredient(decimalName, DECAMT, Units.POUNDS);
        testIngredientFraction = new Ingredient(fractionName, NUMAMT, DENAMT, Units.CUP);
    }


    @Test
    public void testNameMethods(){
        assertEquals(testIngredientDecimal.getName(), fractionName);
        assertEquals(testIngredientFraction.getName(), decimalName);

        String newName = "water";

        testIngredientDecimal.setName(newName);
        assertEquals(testIngredientDecimal.getName(), newName);

        testIngredientFraction.setName(newName);
        assertEquals(testIngredientFraction.getName(), newName);
    }


    @Test
    public void testAmtMethods(){
        Quantity testQ = null;
        double fracDouble = ((double) NUMAMT) / ((double) DENAMT);
        
        testQ = testIngredientDecimal.getAmt();
        assertEquals(testQ.getAmt(), DECAMT, EPSILON);

        testQ = testIngredientFraction.getAmt();
        assertEquals(testQ.getAmt(), fracDouble, EPSILON);

        testIngredientDecimal.setAmt(NUMAMT, DENAMT); // convert to Fraction
        testQ = testIngredientDecimal.getAmt();
        assertEquals(testQ.getAmt(), fracDouble, EPSILON);

        testIngredientFraction.setAmt(DECAMT);  // convert to Decimal
        testQ = testIngredientFraction.getAmt();
        assertEquals(testQ.getAmt(), DECAMT, EPSILON);
    }


    @Test
    public void testEquals(){
        // test duplicates
        Ingredient decDupe = new Ingredient(decimalName, DECAMT, Units.POUNDS);
        Ingredient fracDupe = new Ingredient(fractionName, NUMAMT, DENAMT, Units.CUP);
        assertTrue(fracDupe.equals(testIngredientFraction));
        assertTrue(decDupe.equals(testIngredientDecimal));

        // test pointer shallow copy
        decDupe = testIngredientDecimal;
        fracDupe = testIngredientFraction;
        assertTrue(fracDupe.equals(testIngredientFraction));
        assertTrue(decDupe.equals(testIngredientDecimal));

        Ingredient dummy = new Ingredient("Sludge", 100, Units.DASH);
        assertFalse(dummy.equals(testIngredientDecimal));
        assertFalse(dummy.equals(testIngredientFraction));
    }

}
