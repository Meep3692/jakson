package ca.awoo.jakson;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;

import ca.awoo.fwoabl.Optional;
import ca.awoo.jakson.svalue.SNull;
import ca.awoo.jakson.svalue.SObject;
import ca.awoo.jakson.svalue.SValue;

public class DefaultConverter implements Converter<Object>{

    private final Jakson jakson;

    public DefaultConverter(Jakson jakson) {
        this.jakson = jakson;
    }
    
    public SValue<?> convert(Object value, Class<? extends Object> type) throws ConversionException {
        if(value == null){
            return SNull.INSTANCE;
        }
        SObject sObject = new SObject();
        Field[] fields = type.getDeclaredFields();
        for(Field field : fields){
            if(field.isSynthetic() | Modifier.isTransient(field.getModifiers())){
                continue;
            }
            field.setAccessible(true);
            try {
                Object fieldValue = field.get(value);
                if(value.equals(fieldValue)){
                    throw new ConversionException(value, "Object contains a circular reference in field: " + field.getName());
                }
                if(Optional.class.isAssignableFrom(field.getType())){
                    Optional<?> optional = (Optional<?>) fieldValue;
                    if(optional.isSome()){
                        sObject.put(field.getName(), jakson.convert(optional.get(), optional.get().getClass()));
                    }
                    //If the optional isNone, we don't include the field in the SObject
                } else {
                    sObject.put(field.getName(), jakson.convert(fieldValue, field.getType()));
                }
            } catch (IllegalArgumentException e) {
                throw new ConversionException(value, "Exception thrown while converting field: " + field.getName(), e);
            } catch (IllegalAccessException e) {
                throw new ConversionException(value, "Exception thrown while converting field: " + field.getName(), e);
            } catch (ConversionException e){
                throw new ConversionException(value, "Exception thrown while converting field: " + field.getName(), e);
            }
        }
        return sObject;
    }

    public Object convert(SValue<?> value, Class<? extends Object> type) throws DeconversionException {
        if(value == null){
            throw new DeconversionException(value, "Cannot convert null value");
        }
        if(!(value instanceof SObject)){
            throw new DeconversionException(value, "Expected SObject for conversion to " + type.getName() + ", got " + value.getClass().getName());
        }
        SObject sObject = (SObject) value;
        try{
            Object instance = type.newInstance();
            Field[] fields = type.getDeclaredFields();
            for(Field field : fields){
                try{
                    if(field.isSynthetic() | Modifier.isTransient(field.getModifiers())){
                        continue;
                    }
                    field.setAccessible(true);
                    if(Optional.class.isAssignableFrom(field.getType())){
                        if(sObject.containsKey(field.getName())){
                            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                            Class<?> optionalType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                            field.set(instance, Optional.some(jakson.convert(sObject.get(field.getName()), optionalType)));
                        } else {
                            field.set(instance, Optional.none(field.getType().getTypeParameters()[0].getClass()));
                        }
                    } else {
                        if(sObject.containsKey(field.getName())){
                            field.set(instance, jakson.convert(sObject.get(field.getName()), field.getType()));
                        }else{
                            throw new DeconversionException(value, "Missing field: " + field.getName());
                        }
                    }
                } catch (IllegalAccessException e){
                    throw new DeconversionException(value, "Exception thrown while deconverting field: " + field.getName(), e);
                } catch (DeconversionException e){
                    throw new DeconversionException(value, "Exception thrown while deconverting field: " + field.getName(), e);
                }
            }
            return instance;
        } catch (InstantiationException e) {
            throw new DeconversionException(value, "Exception thrown while converting to " + type.getName(), e);
        } catch (IllegalAccessException e) {
            throw new DeconversionException(value, "Exception thrown while converting to " + type.getName(), e);
        }
    }
    
}
