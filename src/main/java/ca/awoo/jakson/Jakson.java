package ca.awoo.jakson;

import ca.awoo.jakson.svalue.SValue;

public class Jakson {
    public SValue<?> convert(Object value, Class<?> type) throws ConversionException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public <T> T convert(SValue<?> value, Class<? extends T> type) throws DeconversionException {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
