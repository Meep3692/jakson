package ca.awoo.jakson.converter;

import ca.awoo.jakson.svalue.SNumber;
import ca.awoo.jakson.svalue.SValue;

public class DoubleConverter implements Converter<Double>{

    public SValue<?> convert(Double value, Class<? extends Double> type) throws ConversionException {
        return new SNumber(value);
    }

    public Double convert(SValue<?> value, Class<? extends Double> type) throws DeconversionException {
        if(value == null) throw new DeconversionException(value, "Attempt to deconvert a null value to a double.");
        if (value instanceof SNumber) {
            //TODO: do we want to throw an exception if the value is out of range?
            return ((SNumber) value).getValue().doubleValue();
        } else {
            throw new DeconversionException(value, "Attempt to deconvert a non-number value to a double.");
        }
    }
    
}
