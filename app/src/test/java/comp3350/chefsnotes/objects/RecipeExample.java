package comp3350.chefsnotes.objects;

import comp3350.chefsnotes.business.Units;

public class RecipeExample extends Recipe{
    public RecipeExample(){
        super("Chocolate Chip Cookies");
        this.setup();
    }

    public RecipeExample(int i){
        super("Fairy Pie");
        this.setupOther();
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

        this.addDirection("Preheat oven to 350 degrees F (175 degrees C).", "Preheat", 15);
        this.addDirection("Cream together the butter, white sugar, and brown sugar until smooth. Beat in the eggs one at a time, then stir in the vanilla.", "Mix Batter");
        this.addDirection("Dissolve baking soda in hot water. Add to batter along with salt. Stir in flour, chocolate chips, and nuts. Drop by large spoonfuls onto ungreased pans.",
                "Add solids");
        this.addDirection("Bake for about 10 minutes in the preheated oven, or until edges are nicely browned.", "Bake cookies!", 10);
    }
        

    private void setupOther(){
        this.addIngredient(new Ingredient("Flour", 2, "cup"));
        this.addIngredient(new Ingredient("Butter", 1, "cup"));
        this.addIngredient(new Ingredient("Water", 1, "cup"));
        this.addIngredient(new Ingredient("Sugar", 1, "cup"));
        this.addIngredient(new Ingredient("Cornstarch", 5, "tbsp"));
        this.addIngredient(new Ingredient("Freshly Ground Fairies", 3, "cup"));
        this.addIngredient(new Ingredient("Powdered Fairy Wings", 3, "tbsp"));
        this.addIngredient(new Ingredient("Salt", 2, "tsp"));
        this.addIngredient("Fairy Blood", .75, "cup");
        this.addIngredient(new Ingredient("Lemon Juice", 1, "tbsp"));
        this.addIngredient(new Ingredient("Fairy Dust", 3, "tbsp"));

        this.addDirection("Preheat oven to 200C");
        this.addDirection("Make Dough", "Mix together flour butter until crumbly. " +
                "Gradually add water until the dough holds together well, divide into two portions.", 10);
        this.addDirection("Shape Dough", "Dust the surface with flour amd use a roller" +
                "to roll each portion of dough into a 1 cm thick disc.", 30);
        this.addDirection("Whisk together sugar, cornstarch, salt, and freshly ground Fairies.");
        this.addDirection("Simmer Filling", "In a saucepan, combine Fairy blood and lemon juice " +
                "with the whisked dry ingredients and simmer on medium-low for 20 minutes until thick", 30);
        this.addDirection("Transfer one dough portion into a pie plate and trim extra crust. Pour" +
                "in the filling.");
        this.addDirection("Cover pie with remaining dough portion and sprinkle with Fairy dust");
        this.addDirection("Bake", "Bake pie for 1 hour in preheated oven. " +
                "After 30 minutes, check to see if the Fairy dust is sparkling sufficiently. " +
                "If not, sprinkle additional dust.", 60);
        this.addDirection("Remove when the crust becomes brightly multicoloured. " +
                "Cool on wire rack.", 20);

}
}
