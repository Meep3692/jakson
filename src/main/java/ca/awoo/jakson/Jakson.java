package ca.awoo.jakson;

import java.util.HashSet;
import java.util.Set;

import ca.awoo.fwoabl.Pair;
import ca.awoo.jakson.converter.ConversionException;
import ca.awoo.jakson.converter.Converter;
import ca.awoo.jakson.converter.DeconversionException;
import ca.awoo.jakson.svalue.SValue;

public class Jakson {

    private final Set<Pair<ClassMatcher, Converter<?>>> converters = new HashSet<Pair<ClassMatcher, Converter<?>>>();
    private Converter<?> defaultConverter = null;

    private Pair<ClassMatcher, Converter<?>> findConverter(Class<?> type){
        for(Pair<ClassMatcher, Converter<?>> pair : converters){
            if(pair.first.matches(type)){
                return pair;
            }
        }
        return null;
    }

    public void registerConverter(ClassMatcher matcher, Converter<?> converter){
        converters.add(new Pair<ClassMatcher, Converter<?>>(matcher, converter));
    }

    public void registerConverter(Class<?> type, Converter<?> converter){
        registerConverter(new SingleClassMatcher(type), converter);
    }

    public void registerDefaultConverter(Converter<?> converter){
        defaultConverter = converter;
    }

    public SValue<?> convert(Object value, Class<?> type) throws ConversionException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public <T> T convert(SValue<?> value, Class<? extends T> type) throws DeconversionException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Converts a primative class to its object equivalent. Mike Tyson is a boxer.
     * @param primative The primative class to convert
     * @return The object equivalent of the primative class
     */
    private Class<?> mikeTyson(Class<?> primative){
        if(primative == int.class){
            return Integer.class;
        }else if(primative == long.class){
            return Long.class;
        }else if(primative == float.class){
            return Float.class;
        }else if(primative == double.class){
            return Double.class;
        }else if(primative == boolean.class){
            return Boolean.class;
        }else if(primative == char.class){
            return Character.class;
        }else if(primative == byte.class){
            return Byte.class;
        }else if(primative == short.class){
            return Short.class;
        }else{
            return primative;
        }
    }
}
