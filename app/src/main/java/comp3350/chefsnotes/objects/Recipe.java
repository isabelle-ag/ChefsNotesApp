package comp3350.chefsnotes.objects;

import java.io.Serializable;
import java.util.ArrayList;

import comp3350.chefsnotes.application.Services;
import comp3350.chefsnotes.persistence.PhotoList;

// DO NOT CREATE THIS CLASS EXPLICITLY
// methods with names that begin with "_" should only be called by the DBMS
// instances of will be created and managed
// by a DBMS implementing comp3350.chefsnotes.persistance.DBMSTools.java. Refer
// to the comp3350.chefsnotes.persistance.DBMSTools.java interface file to understand
// how to use a DBMS to perform operations on a Recipe.

public class Recipe implements Serializable {
    private static final long serialVersionUID = 202203171213L;

    private String title;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Direction> directions;
    private ArrayList<String> tags;
    private ArrayList<String> photos;   // list of pathnames
    private String notes;

    public Recipe(String t){
        title = t;
        ingredients = new ArrayList<Ingredient>();
        directions = new ArrayList<Direction>();
        tags = new ArrayList<String>();
        photos = new ArrayList<String>();
        notes = "";
    }

    public void addTag(String tag)
    {
        tags.add(tag);
    }

    public void removeTag(String tag)
    {
        if(tags.contains(tag)) {
            tags.remove(tag);
        }
    }

    public ArrayList<String> getTags()
    {
        return tags;
    }

    public boolean hasTag(String tag){
        return(tags.contains(tag));
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

    public void clearIngredients()
    {
        this.ingredients = new ArrayList<Ingredient>();
    }

    public void clearDirections()
    {
        this.directions = new ArrayList<Direction>();
    }

    public void addIngredient(Ingredient i) {
        this.ingredients.add(i);
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
        return this.ingredients.toArray(new Ingredient[0]);
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

    public String[] getIngredientStrings() {
        Ingredient[] ingArray = getIngredients();
        int n = ingArray.length;
        String[] strArray = new String[n];
        for (int i = 0; i < n; i++) {
            strArray[i] = ingArray[i].toString();
        }
        return strArray;
    }

    // deletes first ingredient with matching name
    // fails if ingredient DNE
    // returns true on success, false on failure
    public boolean deleteIngredient(String ingName){
        int target = 0;
        Object factCheck = null;
        boolean result = false;
        boolean found = false;

        for(Ingredient curr : this.ingredients){
            if(curr.getName().equals(ingName)){
                found = true;
                break;
            } else {
                target++;
            }
        }

        if(found) {
            factCheck = ingredients.remove(target);
        }

        if(factCheck != null){
            result = true;
        }

        return result;
    }

    // deletes ingredient at specified index
    // fails if out of bounds
    // returns true on success, false on failure
    public boolean deleteIngredient(int index) throws IndexOutOfBoundsException{
        Object factCheck = null;
        boolean result = false;

        factCheck = ingredients.remove(index); // can throw exception

        if(factCheck != null){
            result = true;
        }

        return result;
    }

    public int addDirection(Direction d) {
        int result = -1;

        boolean test = this.directions.add(d);

        if(test == true){
            result = this.directions.size()-1;
        }

        return result;
    }

    // returns array of all directions
    public Direction[] getDirections(){
        return this.directions.toArray(new Direction[0]);
    }

    // returns single direction via step number
    // returns null if out of bounds
    public Direction getDirection(int dnum) throws IndexOutOfBoundsException{
        Direction result = null;

        result = this.directions.get(dnum); // can throw exception

        return result;
    }

    //For ViewerUI, needs to be an array of strings
    public String[] getDirectionStrings(){
        Direction[] dirArray = getDirections();
        int n = dirArray.length;
        String[] strArray = new String[n+1];
        int totalTime = 0;
        for(int i=0; i<n; i++){
            strArray[i+1]= "Step " + (i+1) + "\t" +"\t"+"\t"+"\t" + dirArray[i].getName() + "\n"  + dirArray[i].getText();
            if(dirArray[i].getTime()>0) {
                totalTime += dirArray[i].getTime();
                strArray[i + 1] += "\nTime: " + dirArray[i].getTime() + " minutes";
            }
        }
        strArray[0] = totalTime + " minutes";
        return strArray;
    }

    // deletes the Direction at index dnum
    // returns false if DNE
    public boolean deleteDirection(int dnum) throws IndexOutOfBoundsException{
        boolean result = true;
        Object test = null;

        test = this.directions.remove(dnum); // can throw exception

        if(test == null){
            result = false;
        }

        return result;
    }

    // attempts to move a Direction from its current position to a new one.
    // position corresponds to index of Direction in the array returned
    // from directionList()
    // reorders Directions. Target direction is moved to the new number,
    // and all other Directions are shifted linearly.
    // 0 < newNum < directionCount
    // returns false if the Direction DNE or the number is out of bounds
    public boolean moveDirection(int oldNum, int newNum){
        boolean result = true;
        Direction tmp = null;

        // try to remove the Direction
        try{
            tmp = this.directions.remove(oldNum);
        } catch (IndexOutOfBoundsException ioobe){
            System.out.println("That Direction number does not exist.");
            tmp = null;
            result = false;
        }

        // try to add the Direction
        if(tmp != null){
            try{
                this.directions.add(newNum, tmp);
                try { // successful add. check name if a number needing change
                    if (Integer.parseInt(Character.toString(tmp.getName().charAt(tmp.getName().length() - 1))) == oldNum) {
                        tmp.setName("Direction " + newNum);
                    }
                } catch (NumberFormatException nfe){
                }
            } catch (IndexOutOfBoundsException ioobe){ // return it to old position on failure
                System.out.println("That target location is out of bounds.");
                this.directions.add(oldNum, tmp);
                result = false;
            }
        }

        return result;
    }

    public boolean addPhoto(String pathname){
        boolean result = false;

        if(!this.photos.contains(pathname)){
            this.photos.add(pathname);              // add to recipe
            PhotoList pl = Services.getPhotoList(); // fetch list
            pl.addReference(pathname);              // add reference (creates if needed)
            result = true;
        }

        return result;
    }

    public boolean removePhoto(String pathname){
        boolean result = false;

        if(this.photos.contains(pathname)){
            this.photos.remove(pathname);           // remove from recipe
            PhotoList pl = Services.getPhotoList(); // fetch list
            if(pl.removeReference(pathname)) {      // remove reference (deletes if necessary)
                result = true;
            }
        }

        return result;
    }

    public String[] getPhotos(){
        return this.photos.toArray(new String[0]);
    }


    public boolean equals(Object other){
        boolean result = this == other;

        if(other instanceof Recipe){
            Recipe casted = (Recipe) other;
            result = this.title.equals(casted.getTitle());
        }

        return result;
    }

    public Recipe clone()
    {
        Recipe out = new Recipe(this.title);
        for (Ingredient i: this.ingredients)
        {
            out.ingredients.add(i.clone());
        }
        for (Direction d: this.directions)
        {
            out.directions.add(d.clone());
        }
        for (String s: this.tags)
        {
            out.tags.add(s);
        }

        for(String curr : this.photos){
            out.addPhoto(curr);  // adds photo to recipe, adds reference to db
        }

        out.notes = this.notes;

        return out;
    }

    public Recipe duplicateRecipe(String dupName){
        Recipe out = this.clone();
        out._setTitle(dupName);
        return out;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public String getNotes()
    {
        return this.notes;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(this.title);
        builder.append("\n");
        if(this.tags.size() > 0)
        {
            builder.append("Recipe tags: ");
            for (String tag : this.tags)
            {
                builder.append(tag);
                builder.append(", ");
            }
            builder.delete(builder.length()-2, builder.length());
            builder.append("\n");
        }

        if(this.ingredients.size() > 0) {
            builder.append("\nIngredients:\n");
            for (Ingredient i : this.ingredients) {
                builder.append(i.toString());
                builder.append("\n");
            }
        }
        if(this.directions.size()>0)
        {
            builder.append("\nDirections:\n");
            for (Direction d : this.directions) {
                builder.append(d.toString());
                builder.append("\n");
            }
        }

        builder.append(this.notes);
        builder.append("\n");

        return builder.toString();
    }

    // this is a destructor-style method for Recipe. Does housekeeping on photos,
    // removing a reference from each one in the tracker list. Please call this
    // before abandoning a Recipe object.
    public void onDelete(){
        PhotoList pl = Services.getPhotoList();
        for(String curr : photos){
            pl.removeReference(curr);   // remove a reference
        }
    }

}

