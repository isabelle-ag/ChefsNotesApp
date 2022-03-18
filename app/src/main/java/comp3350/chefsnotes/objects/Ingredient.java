package comp3350.chefsnotes.objects;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Ingredient implements Serializable {
    private static final long serialVersionUID = 202203171146L;

    private String name;
    private Quantity amount;

    public Ingredient(String iName, double a, String unit){
        this.name = iName;
        this.amount = new Quantity(a, unit);
    }

    public Ingredient(String iName, int numer, int denom, String unit){
        this.name = iName;
        this.amount = new Quantity(numer, denom, unit);
    }

    public String getName(){
        return this.name;
    }

    public void setName(String newName){
        this.name = newName;
    }

    // might be better off done differently. advise caution. edit if necessary.
    // likely only way to perform Fraction Arithmetic properly though
    public Quantity getAmt(){
        return this.amount;
    }

    public String getAmtString(){
        return this.amount.toString();
    }

    public void setAmt(double newAmt){
        this.amount.setAmt(newAmt);
    }

    public void setAmt(int newNume, int newDenom){
        this.amount.setAmt(newNume, newDenom);
    }

    public boolean setUnit(String newUnit){
        this.amount.setUnit(newUnit);
        return true;
    }

    @Override
    @NonNull
    public String toString(){
        String result = "";
        result = amount.toString() + " " + this.name;
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other)
            return true;
        if(! (other instanceof Ingredient ) )
            return false;
        Ingredient that = (Ingredient) other;
        return (this.amount.equals(that.amount) && this.name.equals(that.name));
    }

    @Override
    public int hashCode()
    {
        return this.amount.hashCode() * this.name.hashCode();
    }
}
