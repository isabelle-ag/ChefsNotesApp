package comp3350.chefsnotes.objects;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

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
    @NonNull
    public String toString() {
        return String.format("%s: %s - %d min", name, text, time);
    }

}
