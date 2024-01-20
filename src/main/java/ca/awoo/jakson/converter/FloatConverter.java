package ca.awoo.jakson.converter;

import ca.awoo.jakson.svalue.SNumber;
import ca.awoo.jakson.svalue.SValue;

public class FloatConverter implements Converter<Float> {

    public SValue<?> convert(Float value, Class<? extends Float> type) throws ConversionException {
        return new SNumber(value);
    }

    public Float convert(SValue<?> value, Class<? extends Float> type) throws DeconversionException {
        if(value == null) throw new DeconversionException(value, "Attempt to deconvert a null value to a float.");
        if (value instanceof SNumber) {
            //TODO: do we want to throw an exception if the value is out of range?
            return ((SNumber) value).getValue().floatValue();
        } else {
            throw new DeconversionException(value, "Attempt to deconvert a non-number value to a float.");
        }
    }
    
}
