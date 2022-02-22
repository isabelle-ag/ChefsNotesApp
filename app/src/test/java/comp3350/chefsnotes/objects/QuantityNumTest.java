package comp3350.chefsnotes.objects;

import org.junit.Test;

import static org.junit.Assert.*;

public class QuantityNumTest {
    @Test
    public void testGCD() {
        assertEquals(Fraction.getGCD(4, 2), 2);
        assertEquals(Fraction.getGCD(9, 6), 3);
        assertEquals(Fraction.getGCD(3, 5), 1);
        assertEquals(Fraction.getGCD(4, 6), 2);
        assertEquals(Fraction.getGCD(8, 8), 8);
    }

    @Test
    public void fracMath() {
        Fraction f1 = new Fraction(1, 4);
        Fraction f2 = new Fraction(2, 8);
        assertEquals(f1, f2);

    }

}
