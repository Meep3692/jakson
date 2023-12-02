package ca.awoo.jakson.json;

import ca.awoo.jakson.SerializationException;
import ca.awoo.jakson.Serializer;
import ca.awoo.jakson.json.parsers.JsonParser;
import ca.awoo.jakson.svalue.SValue;
import ca.awoo.praser.InputStreamOf;
import ca.awoo.praser.ParseContext;

public class JsonSerializer implements Serializer<Character> {

    private final JsonParser parser = new JsonParser();

    public SValue<?> deserialize(InputStreamOf<Character> stream) throws SerializationException {
        try{
            ParseContext<Character> context = new ParseContext<Character>(stream);
            return parser.parse(context);
        }catch(Exception e){
            throw new SerializationException("An exception occured while parsing", e);
        }
    }
    
}
