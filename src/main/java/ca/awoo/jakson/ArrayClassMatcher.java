package ca.awoo.jakson;

public class ArrayClassMatcher implements ClassMatcher {

    public boolean matches(Class<?> type) {
        return type.isArray();
    }
    
}
