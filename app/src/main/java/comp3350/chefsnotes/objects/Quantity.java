package comp3350.chefsnotes.objects;

import androidx.annotation.NonNull;

import java.util.Locale;

public class Quantity {
    private QuantityNum amt;
    private String unit;

    public Quantity(double amt, String unit) {
        this.amt = new Decimal(amt);
        this.unit = unit;
    }

    public Quantity(int numerator, int denominator, String unit) {
        this.amt = new Fraction(numerator, denominator);
        this.unit = unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return this.unit;
    }

    public String getAmtStr() {
        return this.amt.toString();
    }

    public double getAmt() {
        return this.amt.doubleValue();
    }

    public void setAmt(int numerator, int denominator) {
        amt = new Fraction(numerator, denominator);
    }

    public void setAmt(double value) {
        amt = new Decimal(value);
    }

    @NonNull
    public String toString() {
        if(!unit.equals(""))
            return String.format(Locale.CANADA, "%s %s%s", amt.toString(), unit, amt.needPlural() ? "s" : "");
        else
            return String.format(Locale.CANADA, "%s", amt.toString()); //for stuff not measured in units - causes grammatical issues e.g.: 2 onion, but that would be tricky to resolve (consider "X large onion(s), in slices"). Yikes.
    }

    @NonNull
    @Override
    public boolean equals(Object other) {
        if(this == other)
            return true;
        if(! (other instanceof Quantity ) )
            return false;
        Quantity that = (Quantity) other;
        return (this.amt.equals(that.amt) && this.unit.equals(that.unit));
    }

}
