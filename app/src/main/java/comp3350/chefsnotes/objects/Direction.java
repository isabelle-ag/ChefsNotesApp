package comp3350.chefsnotes.objects;


import java.io.Serializable;

public class Direction implements Serializable {
    private static final long serialVersionUID = 5109363651430369398L;

    private String name;
    private String text;
    private int time;

    public Direction(String txt, String n, int t){
        this.name = n;
        this.text = txt;
        this.time = t;
    }

    public Direction(String txt, int t){
        this(txt, "", t);
    }

    public Direction(String txt, String n){
        this(txt, n, 0);
    }

    public Direction(String txt){this(txt, "", 0);}

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

    @Override
    public boolean equals(Object other) {
        if(this == other)
            return true;
        if(! (other instanceof Direction ) )
            return false;
        Direction that = (Direction) other;
        return (this.name.equals(that.name) && this.text.equals(that.text) && this.time == that.time);
    }

    @Override
    public int hashCode()
    {
        return this.name.hashCode() * 10000 + this.text.hashCode() * 100 + this.time;
    }
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        if(!this.name.equals("")) {
            builder.append(name);
            builder.append(": ");
        }

        builder.append(text);

        if(this.time > 0)
        {
            builder.append(" - ");
            builder.append(time);
            builder.append(" min");
        }
        return builder.toString();
    }

    public Direction clone()
    {
        return new Direction(this.text, this.name, this.time);
    }
}
