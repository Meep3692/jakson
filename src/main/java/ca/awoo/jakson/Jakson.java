package ca.awoo.jakson;

import java.util.HashSet;
import java.util.Set;

import ca.awoo.fwoabl.Pair;
import ca.awoo.jakson.converter.*;
import ca.awoo.jakson.svalue.SValue;

public class Jakson {

    private final Set<Pair<ClassMatcher, Converter<? extends Object>>> converters = new HashSet<Pair<ClassMatcher, Converter<?>>>();
    private Converter<?> defaultConverter = null;

    private Converter<?> findConverter(Class<?> type){
        for(Pair<ClassMatcher, Converter<?>> pair : converters){
            if(pair.first.matches(type)){
                return pair.second;
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

    public void defaultConfig(){
        registerDefaultConverter(new DefaultConverter(this));
        registerConverter(new ArrayClassMatcher(), new ArrayConverter(this));
        registerConverter(Byte.class, new ByteConverter());
        registerConverter(Boolean.class, new BooleanConverter());
        registerConverter(Character.class, new CharacterConverter());
        registerConverter(Double.class, new DoubleConverter());
        registerConverter(Float.class, new FloatConverter());
        registerConverter(Integer.class, new IntegerConverter());
        registerConverter(Long.class, new LongConverter());
        registerConverter(Short.class, new ShortConverter());
        registerConverter(String.class, new StringConverter());
        registerConverter(new AssignagleClassMatcher(Convertable.class), new InterfaceConverter());
    }

    @SuppressWarnings("unchecked")
    public <T> SValue<?> convert(T value, Class<? extends Object> type) throws ConversionException {
        if(type.isPrimitive()){
            type = mikeTyson(type);
        }
        Converter<T> converter = (Converter<T>) findConverter(type);
        if(converter == null){
            if(defaultConverter == null){
                throw new ConversionException(value, "No converter found for type " + type.getName());
            }else{
                converter = (Converter<T>) defaultConverter;
            }
        }
        return converter.convert(value, (Class<? extends T>)type);
    }

    @SuppressWarnings("unchecked")
    public <T> T convert(SValue<?> value, Class<? extends T> type) throws DeconversionException {
        if(type.isPrimitive()){
            type = (Class<? extends T>) mikeTyson(type);
        }
        Converter<T> converter = (Converter<T>) findConverter(type);
        if(converter == null){
            if(defaultConverter == null){
                throw new DeconversionException(value, "No converter found for type " + type.getName());
            }else{
                converter = (Converter<T>) defaultConverter;
            }
        }
        return converter.convert(value, type);
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
