package ca.awoo.jakson.converter;

import ca.awoo.jakson.svalue.SValue;

public interface Converter<T> {
    SValue<?> convert(T value, Class<? extends T> type) throws ConversionException;
    T convert(SValue<?> value, Class<? extends T> type) throws DeconversionException;
}
