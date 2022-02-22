package comp3350.chefsnotes.objects;

import android.icu.util.Freezable;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.Locale;

public class Fraction extends QuantityNum{
    int numerator;
    int denominator;

    public Fraction(int numerator, int denominator)
    {
        if(denominator == 0)
            throw new ArithmeticException("Fractions cannot have a denominator of 0!");
        this.numerator = numerator;
        this.denominator = denominator;
    }

    @NonNull
    public String toString()
    {
        Fraction canon = canonical(this);
        if(canon.denominator > 1)
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
        return canonical(new Fraction(this.numerator * value, this.denominator));
    }

    public Fraction divideBy(int value)
    {
        return canonical(new Fraction(this.numerator, this.denominator * value));
    }

    @Override
    public int hashCode() {
        Fraction c = canonical(this);
        return c.numerator << 8 + c.denominator;
    }

    public boolean equals(Object other) {
        if(this == other)
            return true;
        if(! (other instanceof Fraction))
            return false;

        Fraction c1 = canonical(this);
        Fraction c2 = canonical((Fraction)other);
        return c1.denominator == c2.denominator && c1.numerator == c2.numerator;
    }

    private static Fraction canonical(Fraction given) {
        int gcd = Fraction.getGCD(given.numerator, given.denominator);
        return new Fraction(given.numerator / gcd, given.denominator / gcd);
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

}
