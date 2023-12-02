package ca.awoo.jakson;

import ca.awoo.jakson.svalue.SValue;

import ca.awoo.praser.InputStreamOf;

public interface Serializer<TToken> {
    public SValue<?> deserialize(InputStreamOf<TToken> stream) throws SerializationException;
}
