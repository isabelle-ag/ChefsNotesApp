package comp3350.chefsnotes.objects;

import comp3350.chefsnotes.business.Units;

public class SampleRecipe extends Recipe{
    public SampleRecipe(){
        super("Chocolate Chip Cookies");
        this.setup();
    }

    private void setup() {
        this.addIngredient("Butter", 1, Units.CUP);
        this.addIngredient("White Sugar", 1, Units.CUP);
        this.addIngredient("Brown Sugar", 1, Units.CUP);
        this.addIngredient("Egg", 2, Units.NONE);
        this.addIngredient("Vanilla Extract", 2, Units.TSP);
        this.addIngredient("Baking Soda", 1, Units.TSP);
        this.addIngredient("Hot Water", 2, Units.TSP);
        this.addIngredient("Salt", 1, 2, Units.TSP);
        this.addIngredient("Flour", 3, Units.CUP);
        this.addIngredient("Semisweet Chocolate Chips", 2, Units.CUP);
        this.addIngredient("Chopped Walnuts", 1, Units.CUP);

        this.addDirection(new Direction("Preheat", "Preheat oven to 350 degrees F (175 degrees C).", 15));
        this.addDirection(new Direction("Mix Batter", "Cream together the butter, white sugar, and brown sugar until smooth. Beat in the eggs one at a time, then stir in the vanilla."));
        this.addDirection(new Direction("Add solids","Dissolve baking soda in hot water. " +
                "Add to batter along with salt. Stir in flour, chocolate chips, and nuts. Drop by large spoonfuls onto ungreased pans."));
        this.addDirection(new Direction("Bake cookies!", "Bake for about 10 minutes in the preheated oven, or until edges are nicely browned.", 10));
    }

}
