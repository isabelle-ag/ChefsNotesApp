package comp3350.chefsnotes.objects;

import java.io.Serializable;

abstract class QuantityNum extends Number implements Serializable {
    private static final long serialVersionUID = 202203171137L;

    public abstract QuantityNum multiplyBy(int value);
    public abstract QuantityNum divideBy(int value);


    public abstract boolean needPlural();

    public abstract boolean equals(Object other);
    public abstract int hashCode();

}
