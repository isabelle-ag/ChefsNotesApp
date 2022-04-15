package comp3350.chefsnotes.objects;

import comp3350.chefsnotes.business.Units;

public class SampleRecipeGenerator {
    public static Recipe[] sampleList(){
        return new Recipe[] {chocolateChip(), tomatoSoup(), butterChicken()};
    }

    private static Recipe chocolateChip(){
        Recipe result = new Recipe("Chocolate Chip Cookies");

        result.addIngredient("Butter", 1, Units.CUP);
        result.addIngredient("White Sugar", 1, Units.CUP);
        result.addIngredient("Brown Sugar", 1, Units.CUP);
        result.addIngredient("Egg", 2, Units.NONE);
        result.addIngredient("Vanilla Extract", 2, Units.TSP);
        result.addIngredient("Baking Soda", 1, Units.TSP);
        result.addIngredient("Hot Water", 2, Units.TSP);
        result.addIngredient("Salt", 1, 2, Units.TSP);
        result.addIngredient("Flour", 3, Units.CUP);
        result.addIngredient("Semisweet Chocolate Chips", 2, Units.CUP);
        result.addIngredient("Chopped Walnuts", 1, Units.CUP);

        result.addDirection(new Direction("Preheat oven to 350 degrees F (175 degrees C).", "Preheat", 15));
        result.addDirection(new Direction("Cream together the butter, white sugar, and brown sugar until smooth. Beat in the eggs one at a time, then stir in the vanilla.", "Mix Batter"));
        result.addDirection(new Direction("Dissolve baking soda in hot water. Add to batter along with salt. Stir in flour, chocolate chips, and nuts. Drop by large spoonfuls onto ungreased pans.",
                "Add solids"));
        result.addDirection(new Direction("Bake for about 10 minutes in the preheated oven, or until edges are nicely browned.", "Bake cookies!", 10));

        return result;
    }

    private static Recipe tomatoSoup(){
        Recipe result = new Recipe("Tomato Soup");



        return result;
    }

    private static Recipe butterChicken(){
        Recipe result = new Recipe("Indian Butter Chicken");

        result.addIngredient("Plain Yogourt",1,2,Units.CUP);
        result.addIngredient("Lemon Juice",1,Units.TBSP);
        result.addIngredient("Turmeric", 1, Units.TSP);
        result.addIngredient("Garam Masala",2,Units.TSP);
        result.addIngredient("Chili Powder",1,2,Units.TSP);
        result.addIngredient("Ground Cumin",1,Units.TSP);
        result.addIngredient("Grated Ginger",1,Units.TBSP);
        result.addIngredient("Garlic Cloves",2,Units.NONE);
        result.addIngredient("Chicken Breast",750,Units.GRAMS);
        result.addIngredient("Butter",2,Units.TBSP);
        result.addIngredient("Tomato Paste",1,Units.CUP);
        result.addIngredient("Heavy Cream",1,Units.CUP);
        result.addIngredient("Sugar",1,Units.TBSP);
        result.addIngredient("Salt",5,4,Units.TSP);

        result.addDirection(new Direction("for an extra smooth sauce, combine the Marinade ingredients (except the chicken) in a food processor and blend until smooth. (I do not do this)","Optional Blitz"));
        result.addDirection(new Direction("Marinade","Combine the Marinade ingredients with the chicken in a bowl. Cover and refrigerate overnight, or up to 24 hours (minimum 3 hrs).",180));
        result.addDirection(new Direction("Cook Chicken","Heat the butter over high heat in a large fry pan. Take the chicken out of the Marinade but do not wipe or shake off the marinade from the chicken (but don't pour the Marinade left in the bowl into the fry pan)."));
        result.addDirection(new Direction("Place chicken in the fry pan and cook for around 3 minutes, or until the chicken is white all over (it doesn't really brown because of the Marinade).",3));
        result.addDirection(new Direction("Sauce","Add the tomato paste, cream, sugar and salt. Also add any remaining marinade left in the bowl. Turn down to low and simmer for 20 minutes. Do a taste test to see if it needs more salt.",20));
        result.addDirection(new Direction("Garnish with coriander/cilantro leaves if using. Serve with basmati rice and naan bread."));

        return result;
    }
}
