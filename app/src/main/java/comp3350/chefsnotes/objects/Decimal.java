package comp3350.chefsnotes.objects;

import androidx.annotation.NonNull;

public class Decimal extends QuantityNum {
    private static double compareEpsilon = 0.000001d;

    double value;

    public Decimal(double value) {
        this.value = value;
    }

    @NonNull
    @Override
    public String toString()
    {
        if(value == Math.floor(value)) //no decimal part
            return Integer.toString((int) value);
        else
            return Double.toString(value);
    }

    @Override
    public int intValue() {
        return (int) value;
    }

    @Override
    public long longValue() {
        return (long) value;
    }

    @Override
    public float floatValue() {
        return (float) value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

    public Decimal multiplyBy(int value)
    {
        return new Decimal(this.value * value);
    }

    public Decimal divideBy(int value)
    {
        return new Decimal(this.value / value);
    }

    @Override
    public boolean needPlural() {
        return this.value != 1;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other)
            return true;
        if(! (other instanceof Decimal))
            return false;
        Decimal that = (Decimal) other;
        return (Math.abs(this.value - that.value) < compareEpsilon);
    }

    @Override
    public int hashCode() {

        return Double.valueOf(value).hashCode();
    }

}
