package ca.awoo.jakson.converter;

import ca.awoo.jakson.svalue.SNumber;
import ca.awoo.jakson.svalue.SValue;

public class ByteConverter implements Converter<Byte> {

    public SValue<?> convert(Byte value, Class<? extends Byte> type) throws ConversionException {
        return new SNumber(value);
    }

    public Byte convert(SValue<?> value, Class<? extends Byte> type) throws DeconversionException {
        if(value == null){
            throw new DeconversionException(value, "Attempt to deconvert a null value to a byte.");
        }
        if (value instanceof SNumber) {
            //TODO: do we want to throw an exception if the value is out of range?
            return ((SNumber) value).getValue().byteValue();
        } else {
            throw new DeconversionException(value, "Attempt to deconvert a non-number value to a byte.");
        }
    }
    
}
