package ca.awoo.jakson.converter;

import java.lang.reflect.Array;

import ca.awoo.jakson.Jakson;
import ca.awoo.jakson.svalue.SArray;
import ca.awoo.jakson.svalue.SValue;

public class ArrayConverter implements Converter<Object>{

    private final Jakson jakson;

    public ArrayConverter(Jakson jakson) {
        this.jakson = jakson;
    }

    private Object[] getArray(Object val){
        if (val instanceof Object[])
            return (Object[])val;
        int arrlength = Array.getLength(val);
        Object[] outputArray = new Object[arrlength];
        for(int i = 0; i < arrlength; ++i){
            outputArray[i] = Array.get(val, i);
        }
        return outputArray;
    }

    public SValue<?> convert(Object value, Class<? extends Object> type) throws ConversionException {
        SArray array = new SArray();
        Object[] arr = getArray(value);
        for (Object o : arr){
            array.add(jakson.convert(o, type.getComponentType()));
        }
        return array;
    }

    public Object convert(SValue<?> value, Class<? extends Object> type) throws DeconversionException {
        if(value == null){
            throw new DeconversionException(value, "Attempt to deconvert a null value to an array.");
        }
        if (value instanceof SArray){
            SArray array = (SArray)value;
            Object obj = Array.newInstance(type.getComponentType(), array.getValue().size());
            for (int i = 0; i < array.getValue().size(); ++i){
                try{
                    Array.set(obj, i, jakson.convert(array.getValue().get(i), type.getComponentType()));
                } catch (DeconversionException e){
                    throw new DeconversionException(value, "Exception while deconverting array element " + i + ".", e);
                }
            }
            return obj;
        } else {
            throw new DeconversionException(value, "Attempt to deconvert a non-array value to an array.");
        }
    }
    
}
