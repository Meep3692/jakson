package ca.awoo.jakson.converter;

import ca.awoo.jakson.svalue.SString;
import ca.awoo.jakson.svalue.SValue;

public class StringConverter implements Converter<String> {

    public SValue<?> convert(String value, Class<? extends String> type) throws ConversionException {
        return new SString(value);
    }

    public String convert(SValue<?> value, Class<? extends String> type) throws DeconversionException {
        if (value instanceof SString) {
            return ((SString) value).getValue();
        } else {
            throw new DeconversionException(value, "Attempt to deconvert a non-string value to a string.");
        }
    }
    
}
