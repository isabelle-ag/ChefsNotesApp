package comp3350.chefsnotes.objects;

import java.io.Serializable;
import java.util.Locale;

public class Quantity implements Serializable {
    private static final long serialVersionUID = 202203171140L;

    private QuantityNum amt;
    private String unit;

    public Quantity(double amt, String unit) {
        this.amt = new Decimal(amt);
        this.unit = unit;
    }

    public Quantity(String unit) {
        this.unit = unit;
        this.amt = new QuantityNumStub();
    }

    public Quantity(int numerator, int denominator, String unit) {
        this.amt = new Fraction(numerator, denominator);
        this.unit = unit;
    }

    private Quantity(QuantityNum quant, String unit)
    {
        this.amt = quant;
        this.unit = unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return this.unit;
    }

    public String getAmtStr() {
        String ret = "";
        if(this.amt != null)
        {
            ret = this.amt.toString();
        }
        return ret;
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

    public String toString() {
        if(!unit.equals(""))
            return String.format(Locale.CANADA, "%s %s%s", amt.toString(), unit, amt.needPlural() ? "s" : "");
        else if(amt != null)
            return String.format(Locale.CANADA, "%s", amt.toString()); //for stuff not measured in units - causes grammatical issues e.g.: 2 onion, but that would be tricky to resolve (consider "X large onion(s), in slices"). Yikes.
        else
            return String.format(Locale.CANADA, "");
    }

    @Override
    public boolean equals(Object other) {
        if(this == other)
            return true;
        if(! (other instanceof Quantity ) )
            return false;
        Quantity that = (Quantity) other;
        return (this.amt.equals(that.amt) && this.unit.equals(that.unit));
    }

    public Quantity clone()
    {
        return new Quantity(this.amt.clone(), this.unit);
    }

}
