package comp3350.chefsnotes.objects;

import comp3350.chefsnotes.business.Units;

public class RecipeExample extends Recipe {
    public RecipeExample(){
        super("#3350TEST");
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

        this.addDirection(new Direction("Preheat oven to 350 degrees F (175 degrees C).", "Preheat", 15));
        this.addDirection(new Direction("Cream together the butter, white sugar, and brown sugar until smooth. Beat in the eggs one at a time, then stir in the vanilla.", "Mix Batter"));
        this.addDirection(new Direction("Dissolve baking soda in hot water. Add to batter along with salt. Stir in flour, chocolate chips, and nuts. Drop by large spoonfuls onto ungreased pans.",
                "Add solids"));
        this.addDirection(new Direction("Bake for about 10 minutes in the preheated oven, or until edges are nicely browned.", "Bake cookies!", 10));
    }

}
