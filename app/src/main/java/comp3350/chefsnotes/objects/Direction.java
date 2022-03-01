package comp3350.chefsnotes.objects;

public class Direction {
    private String name;
    private String text;
    private int time;

    public Direction(String txt, String n, int t){
        this.name = n;
        this.text = txt;
        this.time = t;
    }

    public Direction(String txt, int t){
        this.name = "";
        this.text = txt;
        this.time = t;
    }

    public Direction(String txt, String n){
        this.name = n;
        this.text = txt;
        this.time = 0;
    }

    public Direction(String txt){
        this.name = "";
        this.text = txt;
        this.time = 0;
    }

    public String getName(){
        return this.name;
    }

    public String setName(String newName){
        String result = this.name;
        this.name = newName;
        return result;
    }

    public String getText(){
        return this.text;
    }

    public void setText(String newTxt){
        this.text = newTxt;
    } 

    public int getTime(){
        return this.time;
    }

    public int setTime(int newTime){
        int result = this.time;
        this.time = newTime;
        return result;
    }
}
