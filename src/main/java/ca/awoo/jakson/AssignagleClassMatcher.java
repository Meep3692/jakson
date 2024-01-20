package ca.awoo.jakson;

public class AssignagleClassMatcher implements ClassMatcher{

    private final Class<?> assignable;

    public AssignagleClassMatcher(Class<?> assignable) {
        this.assignable = assignable;
    }

    public boolean matches(Class<?> type) {
        return assignable.isAssignableFrom(type);
    }
    
}
