package comp3350.chefsnotes.objects;

import java.util.ArrayList;

// DO NOT REFER TO THIS CLASS EXPLICITLY
// instances of comp3350.chefsnotes.objects.Recipe will be created and managed
// by a DBMS implementing comp3350.chefsnotes.persistance.DBMSTools.java. Refer
// to the comp3350.chefsnotes.persistance.DBMSTools.java interface file to understand
// how to use a DBMS to perform operations on a comp3350.chefsnotes.objects.Recipe.

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

    // external unique check.
    public void setTitle(String newTitle){
        this.title = newTitle;
    }

    public boolean addIngredient(String newIng, double amount, String unit){
        boolean result = true;

        this.ingredients.add(new Ingredient(newIng, amount, unit));

        return result;
    }

    public boolean addIngredient(String newIng, int numer, int denom, String unit){
        boolean result = true;

        result = this.ingredients.add(new Ingredient(newIng, numer, denom, unit));

        return result;
    }

    public Ingredient[] getIngredients(){
        Ingredient[] result = this.ingredients.toArray(new Ingredient[0]);

        return result;
    }
    
    public String[] ingredientList(){
        String[] result = new String[this.ingredients.size()];
        int i = 0;

        for(Ingredient curr : this.ingredients){
            result[i] = curr.getName();
            i++;
        }

        return result;
    }

    public Ingredient getIngredient(String ingName){
        Ingredient result = null;

        for(Ingredient curr : this.ingredients){
            if(curr.getName().equals(ingName)){
                result = curr;
                break;
            }
        }

        return result;
    }

    public boolean addDirection(String txt, String n, int t){
        boolean result = true;

        result = this.directions.add(new Direction(txt, n, t));

        return result;
    }

    public boolean addDirection(String txt, int t){
        boolean result = true;

        result = this.directions.add(new Direction(txt, t));

        return result;
    }

    public boolean addDirection(String txt, String n){
        boolean result = true;

        result = this.directions.add(new Direction(txt, n));

        return result;
    }

    public Direction[] getDirections(){
        Direction[] result = this.directions.toArray(new Direction[0]);

        return result;
    }

    public Direction getDirection(int dnum){
        Direction result = null;
        
        try{
            result = this.directions.get(dnum);
        } catch (IndexOutOfBoundsException ioobe){
            System.out.println("That comp3350.chefsnotes.objects.Direction number does not exist.");
        }

        return result;
    }

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




}

