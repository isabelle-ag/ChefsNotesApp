package comp3350.chefsnotes.objects;

import java.util.Objects;

public class Photo {
    private String pathname;
    private int refCount;

    public Photo(String p, int r){
        this.pathname = p;
        this.refCount = r;
    }

    public String getPathname() {
        return this.pathname;
    }

    public int getRefCount(){
        return this.refCount;
    }

    // increments and returns new value
    public int addReference(){
        return ++this.refCount;
    }

    // decrements and returns new value -- DOES NOT DELETE THE PHOTO
    // PhotoList should delete on zero.
    public int removeReference(){
        return --this.refCount;
    }

    // NOTE : this is not a *true* override, as it *only* compares pathname. for use in List.contains()
    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if(o == this){
            result = true;
        } else if (o instanceof Photo){
            Photo other = (Photo) o;
            if (this.pathname.equals(other.pathname)){
                result = true;
            }
        }
        return result;
    }

}
