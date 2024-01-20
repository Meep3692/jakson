package ca.awoo.jakson.svalue;

import java.util.HashMap;
import java.util.Map;

public class SObject extends SValue<Map<String, SValue<?>>> {

    public SObject(Map<String, SValue<?>> value) {
        super(value);
    }

    public SObject() {
        this(new HashMap<String, SValue<?>>());
    }

    public SValue<?> get(String key) {
        return getValue().get(key);
    }

    public void put(String key, SValue<?> value) {
        getValue().put(key, value);
    }

    public boolean containsKey(String key) {
        return getValue().containsKey(key);
    }

    public String toString() {
        return getValue().toString();
    }
    
}
