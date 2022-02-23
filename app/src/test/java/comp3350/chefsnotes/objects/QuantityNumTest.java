package comp3350.chefsnotes.objects;

import org.junit.Test;

import static org.junit.Assert.*;

public class QuantityNumTest {
    private final static double EPSILON = 0.00001d;

    @Test
    public void testGCD() {
        assertEquals(Fraction.getGCD(4, 2), 2);
        assertEquals(Fraction.getGCD(9, 6), 3);
        assertEquals(Fraction.getGCD(3, 5), 1);
        assertEquals(Fraction.getGCD(4, 6), 2);
        assertEquals(Fraction.getGCD(8, 8), 8);
    }

    @Test
    public void testfracEquality() {

        Fraction f1 = new Fraction(1, 4);
        Fraction f2 = new Fraction(2, 8);
        Fraction f3 = new Fraction(3, 8);
        Fraction f4 = new Fraction(3, 8);
        Fraction f5 = new Fraction(8);
        Fraction f6 = new Fraction(16, 2);

        assertEquals(f1, f2);
        assertNotEquals(f1, f3);
        assertEquals(f1, f1);
        assertEquals(f3, f4);
        assertEquals(f5, f6);
    }

    @Test
    public void testfracMath() {
        Fraction f1 = new Fraction(1, 4);
        Fraction f2 = new Fraction(2, 8);
        Fraction f3 = new Fraction(8, 8);
        Fraction f4 = new Fraction(4, 8);
        Fraction f5 = new Fraction(4);
        Fraction f6 = new Fraction(8, 3);

        assertEquals(f1.multiplyBy(2), f2.multiplyBy(2));
        assertEquals(f1.multiplyBy(4), f3);

        assertEquals(f3.divideBy(2), f4);

        assertEquals(f5, f6.divideBy(2).multiplyBy(3));
    }

    @Test
    public void testfracString() {
        assertEquals(new Fraction(1, 3).toString(), "1/3");
        assertEquals(new Fraction(9, 6).toString(), "3/2");
        assertEquals(new Fraction(8, 2).toString(), "4");
        assertEquals(new Fraction(1).toString(), "1");
    }

    @Test
    public void testfracValues() {
        assertEquals(new Fraction(1, 3).doubleValue(), 1d/3d, EPSILON);
        assertEquals(new Fraction(1, 7).floatValue(), 1f/7f, EPSILON);
        assertEquals(new Fraction(5, 2).intValue(), 5/2);
        assertEquals(new Fraction(555, 100).longValue(), 555/100);
    }


    @Test
    public void testdecString() {
        assertEquals(new Decimal(10.5).toString(), "10.5");
        assertEquals(new Decimal(10.050).toString(), "10.05");
        assertEquals(new Decimal(0.1).toString(), "0.1");
        assertEquals(new Decimal(1).toString(), "1");
    }

    @Test
    public void testdecValues() {
        assertEquals(new Decimal(10.5).doubleValue(), 10.5d, EPSILON);
        assertEquals(new Decimal(10.050).floatValue(), 10.05f, EPSILON);
        assertEquals(new Decimal(0.1).intValue(), 0);
        assertEquals(new Decimal(1).longValue(), 1);

    }

    @Test
    public void testdecMath() {
        Decimal d1 = new Decimal(0.5);
        Decimal d2 = new Decimal(1.5);
        Decimal d3 = new Decimal(2.5);

        assertEquals(d1.multiplyBy(3), d2);
        assertEquals(d1, d3.divideBy(5));
        assertEquals(d1.multiplyBy(3).doubleValue(), 1.5d, EPSILON);

    }

    @Test
    public void testdecEquality() {
        Decimal d1 = new Decimal(0.5);
        Decimal d2 = new Decimal(0.50);

        assertEquals(d1, d1);
        assertEquals(d1, d2);

        assertEquals(new Decimal(0.10), new Decimal(0.1));

    }

    @Test
    public void testpluralize() {
        assertFalse(new Fraction(1, 2).needPlural());
        assertFalse(new Fraction(2, 2).needPlural());
        assertTrue(new Fraction(5).needPlural());
        assertTrue(new Fraction(3, 2).needPlural());
        assertTrue(new Fraction(0, 4).needPlural());

        assertTrue(new Decimal(5).needPlural());
        assertTrue(new Decimal(0.5).needPlural());
        assertFalse(new Decimal(1).needPlural());
        assertTrue(new Decimal(1.2).needPlural());

    }


}
