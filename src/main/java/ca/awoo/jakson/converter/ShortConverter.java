package ca.awoo.jakson.converter;

import ca.awoo.jakson.svalue.SNumber;
import ca.awoo.jakson.svalue.SValue;

public class ShortConverter implements Converter<Short>{

    public SValue<?> convert(Short value, Class<? extends Short> type) throws ConversionException {
        return new SNumber(value);
    }

    public Short convert(SValue<?> value, Class<? extends Short> type) throws DeconversionException {
        if(value == null){
            throw new DeconversionException(value, "Attempt to deconvert a null value to a short.");
        }
        if (value instanceof SNumber) {
            // TODO: do we want to throw an exception if the value is out of range?
            return ((SNumber) value).getValue().shortValue();
        } else {
            throw new DeconversionException(value, "Attempt to deconvert a non-number value to a short.");
        }
    }
}
