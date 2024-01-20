package ca.awoo.jakson.converter;

import ca.awoo.jakson.svalue.SNumber;
import ca.awoo.jakson.svalue.SValue;

public class IntegerConverter implements Converter<Integer> {

    public SValue<?> convert(Integer value, Class<? extends Integer> type) throws ConversionException {
        return new SNumber(value);
    }

    public Integer convert(SValue<?> value, Class<? extends Integer> type) throws DeconversionException {
        if(value == null) throw new DeconversionException(value, "Attempt to deconvert a null value to an integer.");
        if (value instanceof SNumber) {
            //TODO: do we want to throw an exception if the value is out of range?
            return ((SNumber) value).getValue().intValue();
        } else {
            throw new DeconversionException(value, "Attempt to deconvert a non-number value to an integer.");
        }
    }
    
}
