public abstract class Units {
    public static final String ML = "mL";
    public static final String L = "L";
    public static final String CUP = "cup";
    public static final String OUNCE = "oz";
    public static final String TBSP = "tbsp";
    public static final String TSP = "tsp";
    public static final String DASH = "dash";
    public static final String QUART = "quart";
    public static final String GALLON = "gallon";
    public static final String PINT = "pint";
    public static final String PIECE = "piece";
    public static final String UNIT = "unit";

    public static boolean isUnit(String test){
        boolean result = false;

        if(test.equals(ML) || test.equals(L) || test.equals(CUP) || test.equals(OUNCE) ||
            test.equals(TBSP) || test.equals(TSP) || test.equals(DASH) || test.equals(QUART) ||
                test.equals(GALLON) || test.equals(PINT) || test.equals(PIECE) || test.equals(UNIT)){
            
            result = true;
        }

        return result;
    }
}
