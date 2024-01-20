package ca.awoo.jakson;

public class SingleClassMatcher implements ClassMatcher {

    private final Class<?> clazz;

    public SingleClassMatcher(Class<?> clazz) {
        this.clazz = clazz;
    }

    public boolean matches(Class<?> type) {
        return clazz.equals(type);
    }
    
}
