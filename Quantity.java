public class Quantity {

    private int amount;
    private String unit;

    public Quantity(int a, String u){
        amount = a;
        unit = u;
        // if invalid, set to UNIT as default
        if(!Units.isUnit(unit)){
            unit = Units.UNIT;
        }
    }

    public int getAmount(){
        return amount;
    }

    public String getUnit(){
        return unit;
    }

    // sets the new amount, returns the old one
    public int setAmount(int newAmount){
        int result = amount;
        amount = newAmount;
        return result;
    }

    // sets new unit, returns old one
    // fails if unit is not a registered unit
    // returns null on failure
    public String setUnit(String newUnit){
        String result = null;

        if(Units.isUnit(newUnit)){
            result = unit;
            unit = newUnit;
        }

        return result;
    }
}
