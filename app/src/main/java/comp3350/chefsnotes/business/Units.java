package comp3350.chefsnotes.business;

public abstract class Units {
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
        return new String[] {NONE, ML, L, FLUID_OUNCE, DASH, TSP, TBSP, CUP, PINT, QUART, GALLON, PIECE, UNIT, POUNDS, GRAMS, KILOGRAMS};
    }

    public static boolean isUnit(String test){
        boolean result = test.equals(ML) || test.equals(L) || test.equals(CUP) || test.equals(FLUID_OUNCE) ||
                test.equals(TBSP) || test.equals(TSP) || test.equals(DASH) || test.equals(QUART) ||
                test.equals(GALLON) || test.equals(PINT) || test.equals(PIECE) || test.equals(UNIT) || test.equals(OUNCE) ||
                test.equals(POUNDS) || test.equals(KILOGRAMS) || test.equals(GRAMS) || test.equals(NONE);

        return result;
    }
}
