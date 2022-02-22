package comp3350.chefsnotes.objects;

import androidx.annotation.NonNull;

import java.util.Locale;

public class Quantity {
    private int amt;
    private String unit;

    public Quantity(int amt, String unit) {
        this.amt = amt;
        this.unit = unit;
    }

        public int getAmt() {
        return amt;
    }

    public void setAmt(int amt) {
        this.amt = amt;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return this.unit;
    }

    @NonNull
    public String toString() {
        return String.format(Locale.CANADA, "%d %s", amt, unit);
    }
}