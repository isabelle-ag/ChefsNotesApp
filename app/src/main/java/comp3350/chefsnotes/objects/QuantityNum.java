package comp3350.chefsnotes.objects;

import java.io.Serializable;

public abstract class QuantityNum extends Number implements Serializable {
    public abstract QuantityNum multiplyBy(int value);
    public abstract QuantityNum divideBy(int value);


    public boolean needPlural() {
        return this.doubleValue() > 1;
    }

    public abstract boolean equals(Object other);
    public abstract int hashCode();

}
