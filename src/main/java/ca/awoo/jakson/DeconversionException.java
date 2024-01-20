package ca.awoo.jakson;

import ca.awoo.jakson.svalue.SValue;

public class DeconversionException extends Exception {
    public final SValue<?> toConvert;

    public DeconversionException(SValue<?> toConvert, String message) {
        super(toConvert.toString() + message);
        this.toConvert = toConvert;
    }

    public DeconversionException(SValue<?> toConvert, String message, Throwable cause) {
        super(toConvert.toString() + message, cause);
        this.toConvert = toConvert;
    }

    public DeconversionException(SValue<?> toConvert, Throwable cause) {
        super(toConvert.toString(), cause);
        this.toConvert = toConvert;
    }

    public DeconversionException(SValue<?> toConvert) {
        super(toConvert.toString());
        this.toConvert = toConvert;
    }

}
