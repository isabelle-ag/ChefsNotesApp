package comp3350.chefsnotes.objects;

import java.util.Locale;

class Fraction extends QuantityNum{
    int numerator;
    int denominator;

    public Fraction(int numerator) {
        this.numerator = numerator;
        this.denominator = 1;
    }

    public Fraction(int numerator, int denominator)
    {
        if(denominator == 0)
            throw new ArithmeticException("Fractions cannot have a denominator of 0!");
        //into canonical form
        int gcd = getGCD(numerator, denominator);
        this.numerator = numerator / gcd;
        this.denominator = denominator / gcd;
    }

    public String toString()
    {
        if(this.denominator > 1)
            return String.format(Locale.CANADA, "%d/%d", numerator, denominator);
        else
            return String.format(Locale.CANADA, "%d", numerator);
    }

    @Override
    public double doubleValue()
    {
        return (double) numerator / (double) denominator;
    }

    @Override
    public int intValue()
    {
        return (int) this.doubleValue();
    }

    @Override
    public long longValue()
    {
        return (int) this.doubleValue();
    }

    @Override
    public float floatValue()
    {
        return (float) this.doubleValue();
    }

    public Fraction multiplyBy(int value)
    {
        return new Fraction(this.numerator * value, this.denominator);
    }

    public Fraction divideBy(int value)
    {
        return new Fraction(this.numerator, this.denominator * value);
    }

    @Override
    public boolean needPlural() {
        return this.doubleValue() > 1 || this.doubleValue() == 0;
    }

    @Override
    public int hashCode() {
        return this.numerator << 8 + this.denominator;
    }

    public boolean equals(Object other) {
        if(this == other)
            return true;
        if(! (other instanceof Fraction))
            return false;

        Fraction that = (Fraction)other;
        return this.denominator == that.denominator && this.numerator == that.numerator;
    }

    public static int getGCD (int numerator, int denominator)
    {
        if(numerator > denominator)
        {
            return Fraction.gcd(numerator, denominator);
        }
        else
        {
            return Fraction.gcd(denominator, numerator);
        }
    }

    private static int gcd (int big, int small) {
        if(small == 0)
            return big;
        else
            return gcd(small, big % small);
    }

    public Fraction clone()
    {
        return new Fraction(this.numerator, this.denominator);
    }

}
