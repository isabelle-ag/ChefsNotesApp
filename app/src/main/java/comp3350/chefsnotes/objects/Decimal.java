package comp3350.chefsnotes.objects;

public class Decimal extends QuantityNum {
    private static final double compareEpsilon = 0.000001d;

    double value;

    public Decimal(double value) {
        this.value = value;
    }

    @Override
    public String toString()
    {
        if(Math.abs(value - Math.floor(value)) < compareEpsilon) //no decimal part
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
        return !(this.equals(new Decimal(1)));
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

    public Decimal clone()
    {
        return new Decimal(this.value);
    }

}
