package ca.awoo.jakson.svalue;

import java.util.ArrayList;
import java.util.List;

public class SArray extends SValue<List<SValue<?>>>{

    public SArray(List<SValue<?>> value) {
        super(value);
    }

    public SArray() {
        super(new ArrayList<SValue<?>>());
    }

    public SValue<?> get(int index) {
        return getValue().get(index);
    }

    public void add(SValue<?> value) {
        getValue().add(value);
    }

    
}
