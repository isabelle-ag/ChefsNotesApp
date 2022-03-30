package comp3350.chefsnotes.objects;

public class SampleRecipe extends Recipe{
    public SampleRecipe(){
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
        this.addIngredient("Fairy Blood", 3,4, "cup");
        this.addIngredient(new Ingredient("Lemon Juice", 1, "tbsp"));
        this.addIngredient(new Ingredient("Fairy Dust", 3, "tbsp"));

        this.addDirection(new Direction("Preheat oven to 200C"));
        this.addDirection(new Direction("Make Dough", "Mix together flour butter until crumbly. " +
                "Gradually add water until the dough holds together well, divide into two portions.", 10));
        this.addDirection(new Direction("Shape Dough", "Dust the surface with flour and use a roller" +
                "to roll each portion of dough into a 1 cm thick disc.", 30));
        this.addDirection(new Direction("Whisk together sugar, cornstarch, salt, and freshly ground Fairies."));
        this.addDirection(new Direction("Simmer Filling", "In a saucepan, combine Fairy blood and lemon juice " +
                "with the whisked dry ingredients and simmer on medium-low for 20 minutes until thick", 30));
        this.addDirection(new Direction("Transfer one dough portion into a pie plate and trim extra crust. Pour" +
                "in the filling."));
        this.addDirection(new Direction("Cover pie with remaining dough portion and sprinkle with Fairy dust"));
        this.addDirection(new Direction("Bake", "Bake pie for 1 hour in preheated oven. " +
                "After 30 minutes, check to see if the Fairy dust is sparkling sufficiently. " +
                "If not, sprinkle additional dust.", 60));
        this.addDirection(new Direction("Remove when the crust becomes brightly multicoloured. " +
                "Cool on wire rack."));

    }
}
