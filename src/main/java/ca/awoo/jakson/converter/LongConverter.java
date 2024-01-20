package ca.awoo.jakson.converter;

import ca.awoo.jakson.svalue.SNumber;
import ca.awoo.jakson.svalue.SValue;

public class LongConverter implements Converter<Long> {

    public SValue<?> convert(Long value, Class<? extends Long> type) throws ConversionException {
        return new SNumber(value);
    }

    public Long convert(SValue<?> value, Class<? extends Long> type) throws DeconversionException {
        if(value == null) throw new DeconversionException(value, "Attempt to deconvert a null value to a long.");
        if (value instanceof SNumber) {
            // TODO: do we want to throw an exception if the value is out of range?
            return ((SNumber) value).getValue().longValue();
        } else {
            throw new DeconversionException(value, "Attempt to deconvert a non-number value to a long.");
        }
    }
}
