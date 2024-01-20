package ca.awoo.jakson.converter;

import ca.awoo.jakson.svalue.SBoolean;
import ca.awoo.jakson.svalue.SValue;

public class BooleanConverter implements Converter<Boolean>{

    public SValue<?> convert(Boolean value, Class<? extends Boolean> type) throws ConversionException {
        return new SBoolean(value);
    }

    public Boolean convert(SValue<?> value, Class<? extends Boolean> type) throws DeconversionException {
        if(value == null){
            throw new DeconversionException(value, "Attempt to deconvert a null value to a boolean.");
        }
        if (value instanceof SBoolean) {
            return ((SBoolean) value).getValue();
        } else {
            throw new DeconversionException(value, "Attempt to deconvert a non-boolean value to a boolean.");
        }
    }
    
}
