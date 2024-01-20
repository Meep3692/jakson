package ca.awoo.jakson.converter;

import ca.awoo.jakson.svalue.SString;
import ca.awoo.jakson.svalue.SValue;

public class CharacterConverter implements Converter<Character> {

    public SValue<?> convert(Character value, Class<? extends Character> type) throws ConversionException {
        return new SString(value.toString());
    }

    public Character convert(SValue<?> value, Class<? extends Character> type) throws DeconversionException {
        if(value == null) throw new DeconversionException(value, "Attempt to deconvert a null value to a character.");
        if (value instanceof SString) {
            String str = ((SString) value).getValue();
            if (str.length() == 1) {
                return str.charAt(0);
            } else {
                throw new DeconversionException(value, "Attempt to deconvert a string of length " + str.length() + " to a character.");
            }
        } else {
            throw new DeconversionException(value, "Attempt to deconvert a non-string value to a character.");
        }
    }
    
}
