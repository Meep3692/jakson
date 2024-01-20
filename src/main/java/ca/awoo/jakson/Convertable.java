package ca.awoo.jakson;

import ca.awoo.jakson.converter.ConversionException;
import ca.awoo.jakson.converter.DeconversionException;
import ca.awoo.jakson.svalue.SValue;

public interface Convertable {
    public SValue<?> convert() throws ConversionException;
    public void deconvert(SValue<?> value) throws DeconversionException;
}
