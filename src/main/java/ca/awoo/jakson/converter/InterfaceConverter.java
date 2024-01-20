package ca.awoo.jakson.converter;

import ca.awoo.jakson.Convertable;
import ca.awoo.jakson.svalue.SValue;

public class InterfaceConverter implements Converter<Convertable>  {

    public SValue<?> convert(Convertable value, Class<? extends Convertable> type) throws ConversionException {
        return value.convert();
    }

    public Convertable convert(SValue<?> value, Class<? extends Convertable> type) throws DeconversionException {
        try {
            Convertable convertable = type.newInstance();
            convertable.deconvert(value);
            return convertable;
        } catch (InstantiationException e) {
            throw new DeconversionException(value, "Could not instantiate " + type.getName() + " for deconversion.", e);
        } catch (IllegalAccessException e) {
            throw new DeconversionException(value, "Could not instantiate " + type.getName() + " for deconversion.", e);
        }
    }
    
}
