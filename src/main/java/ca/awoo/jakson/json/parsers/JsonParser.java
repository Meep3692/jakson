package ca.awoo.jakson.json.parsers;

import ca.awoo.jakson.svalue.SValue;
import ca.awoo.praser.ParseContext;
import ca.awoo.praser.ParseException;
import ca.awoo.praser.Parser;
import ca.awoo.praser.StreamException;

public class JsonParser implements Parser<Character, SValue<?>> {

    private final JsonNumberParser numberParser = new JsonNumberParser();
    private final JsonStringParser stringParser = new JsonStringParser();

    public SValue<?> parse(ParseContext<Character> context) throws ParseException {
        try{
            context.push();
            try{
                SValue<?> match = numberParser.parse(context);
                context.merge();
                return match;
            }catch (ParseException e){
                context.pop();
            }
            try{
                SValue<?> match = stringParser.parse(context);
                context.merge();
                return match;
            }catch (ParseException e){
                context.pop();
            }
            throw new ParseException("Expected json value");
        }catch(StreamException e){
            throw new ParseException("An exception occured while parsing", e);
        }
    }
    
}
