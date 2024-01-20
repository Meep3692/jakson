package ca.awoo.jakson.svalue;

public class SNull extends SValue<Object> {

    public static final SNull INSTANCE = new SNull(null);

    private SNull(Object value) {
        super(value);
    }
    
}
