package comp3350.chefsnotes.business;

public abstract class Units { //this is intentionally not an Enum. doing it like this was easier for us to work with.
    public static final String NONE = "";
    public static final String ML = "mL";
    public static final String L = "L";
    public static final String FLUID_OUNCE = "fl. oz";
    public static final String DASH = "dash";
    public static final String TSP = "tsp";
    public static final String TBSP = "tbsp";
    public static final String CUP = "cup";
    public static final String PINT = "pint";
    public static final String QUART = "quart";
    public static final String GALLON = "gallon";
    public static final String PIECE = "piece";
    public static final String UNIT = "unit";
    public static final String OUNCE = "oz";
    public static final String POUNDS = "lbs";
    public static final String GRAMS = "g";
    public static final String KILOGRAMS = "kg";

    public static String[] unitList(){
        return new String[] {NONE, ML, L, FLUID_OUNCE, DASH, TSP, TBSP, CUP, PINT, QUART, GALLON, PIECE, OUNCE, UNIT, POUNDS, GRAMS, KILOGRAMS};
    }

    public static boolean isUnit(String test){
        boolean result = false;
        for(String u : Units.unitList()) {
            if (test.equals(u))
                result = true;
        }
        return result;
    }
}
