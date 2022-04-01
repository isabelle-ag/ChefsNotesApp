package comp3350.chefsnotes.objects;

import comp3350.chefsnotes.objects.QuantityNum;

public class QuantityNumStub extends QuantityNum {

    @Override
    public QuantityNum multiplyBy(int value) {
        return this;
    }

    @Override
    public QuantityNum divideBy(int value) {
        return this;
    }

    @Override
    public boolean needPlural() {
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof QuantityNumStub);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public QuantityNum clone() {
        return new QuantityNumStub();
    }

    @Override
    public int intValue() {
        return 0;
    }

    @Override
    public long longValue() {
        return 0;
    }

    @Override
    public float floatValue() {
        return 0;
    }

    @Override
    public double doubleValue() {
        return 0;
    }

    public String toString() {
        return "";
    }
}
