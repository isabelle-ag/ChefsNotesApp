package comp3350.chefsnotes.business;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UnitsTest {

    @Test
    public void testUnits()
    {
        assertEquals(17, Units.unitList().length);

        assertTrue(Units.isUnit(""));
        assertTrue(Units.isUnit("mL"));
        assertTrue(Units.isUnit("L"));
        assertTrue(Units.isUnit("fl. oz"));
        assertTrue(Units.isUnit("dash"));
        assertTrue(Units.isUnit("tsp"));
        assertTrue(Units.isUnit("tbsp"));
        assertTrue(Units.isUnit("cup"));
        assertTrue(Units.isUnit("pint"));
        assertTrue(Units.isUnit("quart"));
        assertTrue(Units.isUnit("gallon"));
        assertTrue(Units.isUnit("piece"));
        assertTrue(Units.isUnit("unit"));
        assertTrue(Units.isUnit("oz"));
        assertTrue(Units.isUnit("lbs"));
        assertTrue(Units.isUnit("g"));
        assertTrue(Units.isUnit("kg"));

        assertFalse(Units.isUnit("notUnit"));
    }
}
