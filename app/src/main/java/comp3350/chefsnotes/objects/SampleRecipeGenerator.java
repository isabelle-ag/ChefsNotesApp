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

        result.addTag("Sweet");

        result.setNotes("Try with peanut butter chips!\n");

        return result;
    }

    private static Recipe tomatoSoup(){
        Recipe result = new Recipe("Tomato Soup");

        result.addIngredient("Butter",4,Units.TBSP);
        result.addIngredient("Yellow Onion",1,Units.NONE);
        result.addIngredient("Minced Garlic",1,Units.TBSP);
        result.addIngredient("Crushed Tomato",50,Units.OUNCE);
        result.addIngredient("Chicken Stock",1,Units.QUART);
        result.addIngredient("Basil",1,4,Units.CUP);
        result.addIngredient("Sugar",1,Units.TBSP);
        result.addIngredient("Black Pepper",1,2,Units.TSP);
        result.addIngredient("Cream",250,Units.ML);
        result.addIngredient("Parmesan Cheese",1,2,Units.CUP);

        result.addDirection(new Direction("Heat a non-reactive pot over medium heat. Melt in 4 Tbsp butter then sautee onions until softened and golden (10-12 min). Add minced garlic and saute another minute.","Saute Aromatics",10));
        result.addDirection(new Direction("Stir in crushed tomatoes with their juice, your chicken stock, chopped basil, sugar and black pepper. Bring to a boil then reduce heat, partially cover and simmer 10 minutes.","Make Soup Base",10));
        result.addDirection(new Direction("Stir in the cream.","Add Cream"));
        result.addDirection(new Direction("Garnish with parmesan, more basil, and 3 whistles. Serve.","Serve and Garnish"));

        result.addTag("Savory");
        result.addTag("Greek");

        result.setNotes("Try blending for a smoother soup.\n\nDon't forget hotpads next time!\n");

        return result;
    }

    private static Recipe butterChicken(){
        Recipe result = new Recipe("Indian Butter Chicken");

        result.addIngredient("Plain Yoghurt",1,2,Units.CUP);
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

        result.addDirection(new Direction("For an extra smooth sauce, combine the Marinade ingredients (except the chicken) in a food processor and blend until smooth. (I do not do this)","Optional Blitz"));
        result.addDirection(new Direction("Combine the Marinade ingredients with the chicken in a bowl. Cover and refrigerate overnight, or up to 24 hours (minimum 3 hrs).","Marinade",180));
        result.addDirection(new Direction("Heat the butter over high heat in a large fry pan. Take the chicken out of the Marinade but do not wipe or shake off the marinade from the chicken (but don't pour the Marinade left in the bowl into the fry pan).", "Cook Chicken"));
        result.addDirection(new Direction("Place chicken in the fry pan and cook for around 3 minutes, or until the chicken is white all over (it doesn't really brown because of the Marinade).",3));
        result.addDirection(new Direction("Add the tomato paste, cream, sugar and salt. Also add any remaining marinade left in the bowl. Turn down to low and simmer for 20 minutes. Do a taste test to see if it needs more salt.","Sauce",20));
        result.addDirection(new Direction("Garnish with coriander/cilantro leaves if using. Serve with basmati rice and naan bread."));

        result.addTag("Indian");
        result.addTag("Spicy");

        result.setNotes("Use cayenne if out of chili powder.\n\nCook rice as normal.\n");

        return result;
    }
}
