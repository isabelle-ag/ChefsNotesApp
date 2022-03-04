package comp3350.chefsnotes.objects;

public class OtherRecipeExample extends Recipe{
    public OtherRecipeExample(){
        super("Fairy Pie");
        this.setup();
    }


    private void setup(){
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
