package ca.awoo.jakson.svalue;

public class SValue<T> {
    private final T value;

    public SValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public String toString() {
        return value.toString();
    }
}
