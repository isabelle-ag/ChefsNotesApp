public class Ingredient {
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
    }

    public String toString(){
        String result = "";
        result = amount.toString() + " " + this.name;
        return result;
    }
}
