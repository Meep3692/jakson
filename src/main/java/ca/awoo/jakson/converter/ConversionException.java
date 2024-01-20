package ca.awoo.jakson.converter;

public class ConversionException extends Exception {

    public final Object toConvert;

    public ConversionException(Object toConvert, String message) {
        super(toConvert.toString() + message);
        this.toConvert = toConvert;
    }

    public ConversionException(Object toConvert, String message, Throwable cause) {
        super(toConvert.toString() + message, cause);
        this.toConvert = toConvert;
    }

    public ConversionException(Object toConvert, Throwable cause) {
        super(toConvert.toString(), cause);
        this.toConvert = toConvert;
    }

    public ConversionException(Object toConvert) {
        super(toConvert.toString());
        this.toConvert = toConvert;
    }
}
