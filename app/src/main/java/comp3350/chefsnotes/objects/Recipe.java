package comp3350.chefsnotes.objects;

import java.util.ArrayList;

// DO NOT CREATE THIS CLASS EXPLICITLY
// methods with names that begin with "_" should only be called by the DBMS
// instances of will be created and managed
// by a DBMS implementing comp3350.chefsnotes.persistance.DBMSTools.java. Refer
// to the comp3350.chefsnotes.persistance.DBMSTools.java interface file to understand
// how to use a DBMS to perform operations on a Recipe.

public class Recipe {
    
    private String title;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Direction> directions;
    
    public Recipe(String t){
        title = t;
        ingredients = new ArrayList<Ingredient>();
        directions = new ArrayList<Direction>();
    }

    public int ingredientCount(){
        return this.ingredients.size();
    }

    public int directionCount(){
        return this.directions.size();
    }

    public String getTitle(){
        return this.title;
    }

    // change the recipe name. Should be done via the DBMS
    // to ensure uniqueness of name.
    public void _setTitle(String newTitle){
        this.title = newTitle;
    }

    // adds the ingredient specified as a decimal. 
    // Fails if amount <= 0. 
    // returns true on success, false on failure
    public boolean addIngredient(String newIng, double amount, String unit){
        boolean result = false;
        
        if(amount > 0){
            result = this.ingredients.add(new Ingredient(newIng, amount, unit));
        }

        return result;
    }

    // adds the ingredient specified as a fraction. 
    // Fails if amount <= 0. 
    // returns true on success, false on failure
    public boolean addIngredient(String newIng, int numer, int denom, String unit){
        boolean result = false;

        if((numer > 0) && (denom > 0)){
            result = this.ingredients.add(new Ingredient(newIng, numer, denom, unit));
        }

        return result;
    }

    // returns array of all Ingredients in recipe
    // returns null if none exists
    public Ingredient[] getIngredients(){
        Ingredient[] result = null;
        
        if(this.ingredients.size() > 0){
            result = this.ingredients.toArray(new Ingredient[0]);
        }

        return result;
    }
    
    // returns array of all ingredient NAMES in recipe
    public String[] ingredientList(){
        String[] result = new String[this.ingredients.size()];
        int i = 0;

        for(Ingredient curr : this.ingredients){
            result[i] = curr.getName();
            i++;
        }

        return result;
    }

    // returns a specific comp3350.chefsnotes.objects.Ingredient
    // returns null if not found
    // might be changed to return array of all whose names match?
    public Ingredient getIngredient(String ingName){
        Ingredient result = null;

        for(Ingredient curr : this.ingredients){
            if(curr.getName().equals(ingName)){
                result = curr;
                break; // stop looking if found
            }
        }

        return result;
    }

    // deletes first ingredient with matching name
    // fails if ingredient DNE
    // returns true on success, false on failure
    public boolean deleteIngredient(String ingName){
        int target = 0;
        Object factCheck = null;
        boolean result = false;

        for(Ingredient curr : this.ingredients){
            if(curr.getName().equals(ingName)){
                factCheck = ingredients.remove(target);
            }
            target++;
        }

        if(factCheck != null){
            result = true;
        }

        return result;
    }

    // deletes ingredient at specified index
    // fails if out of bounds
    // returns true on success, false on failure
    public boolean deleteIngredient(int index){
        Object factCheck = null;
        boolean result = false;

        try{
            factCheck = ingredients.remove(index);
        } catch (IndexOutOfBoundsException ioobe){
            System.out.println("That comp3350.chefsnotes.objects.Ingredient number does not exist.");
        }

        if(factCheck != null){
            result = true;
        }

        return result;
    }
    
    // names the direction "Direction <num>" by default
    // returns new direction's number, -1 on failure
    public int addDirection(String txt){
        int result = -1;

        boolean test = this.directions.add(new Direction(txt, "Direction "+this.directions.size()));

        if(test = true){
            result = this.directions.size()-1;
        }

        return result;
    }

    // addDirection with all fields
    public int addDirection(String txt, String n, int t){
        int result = -1;

        boolean test = this.directions.add(new Direction(txt, n, t));

        if(test = true){
            result = this.directions.size()-1;
        }

        return result;
    }

    // addDirection with time field
    public int addDirection(String txt, int t){
        int result = -1;

        boolean test = this.directions.add(new Direction(txt, "Direction "+this.directions.size(), t));

        if(test = true){
            result = this.directions.size()-1;
        }

        return result;
    }

    // addDirection but with a direction name
    public int addDirection(String txt, String n){
        int result = -1;

        boolean test = this.directions.add(new Direction(txt, n));

        if(test = true){
            result = this.directions.size()-1;
        }

        return result;
    }

    // returns array of all directions
    public Direction[] getDirections(){
        Direction[] result = this.directions.toArray(new Direction[0]);

        return result;
    }

    // returns single direction via step number
    // returns null if out of bounds
    public Direction getDirection(int dnum){
        Direction result = null;
        
        try{
            result = this.directions.get(dnum);
        } catch (IndexOutOfBoundsException ioobe){
            System.out.println("That comp3350.chefsnotes.objects.Direction number does not exist.");
        }

        return result;
    }

    // deletes the Direction at index dnum
    // returns false if DNE
    public boolean deleteDirection(int dnum){
        boolean result = false;

        try{
            this.directions.remove(dnum);
            result = true;
        } catch (IndexOutOfBoundsException ioobe){
            System.out.println("That comp3350.chefsnotes.objects.Direction number does not exist.");
            result = false;
        }

        return result;
    }

    // attempts to move a comp3350.chefsnotes.objects.Direction from its current position to a new one.
    // position corresponds to index of comp3350.chefsnotes.objects.Direction in the array returned
    // from directionList()
    // reorders Directions. Target direction is moved to the new number,
    // and all other Directions are shifted linearly.
    // 0 < newNum < directionCount
    // returns false if the comp3350.chefsnotes.objects.Direction DNE or the number is out of bounds
    public boolean moveDirection(int oldNum, int newNum){
        boolean result = true;
        Direction tmp = null;

        // try to remove the comp3350.chefsnotes.objects.Direction
        try{
            tmp = this.directions.remove(oldNum);
        } catch (IndexOutOfBoundsException ioobe){
            System.out.println("That comp3350.chefsnotes.objects.Direction number does not exist.");
            tmp = null;
            result = false;
        }

        // try to add the comp3350.chefsnotes.objects.Direction
        if(tmp != null){
            try{
                this.directions.add(newNum, tmp);
            } catch (IndexOutOfBoundsException ioobe){ // return it to old position on failure
                System.out.println("That target location is out of bounds.");
                this.directions.add(oldNum, tmp);
                result = false;
            }
        }

        return result;
    } 


    boolean equals(Recipe target){
        return (this.name.equals(target.getName()));
    }


}

