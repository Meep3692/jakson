package ca.awoo.jakson.json.parsers;

import java.math.BigDecimal;
import java.util.Collection;

import ca.awoo.fwoabl.Optional;
import ca.awoo.jakson.svalue.SNumber;
import ca.awoo.praser.ParseContext;
import ca.awoo.praser.ParseException;
import ca.awoo.praser.Parser;
import ca.awoo.praser.StreamException;
import ca.awoo.praser.character.CharInStringParser;
import ca.awoo.praser.character.SingleCharacterParser;
import ca.awoo.praser.parsers.OrParser;
import ca.awoo.praser.parsers.ZeroOrMoreParser;
import ca.awoo.praser.parsers.ZeroOrOneParser;

public class JsonNumberParser implements Parser<Character, SNumber>{

    private final Parser<Character, Optional<Character>> optionalMinus = new ZeroOrOneParser<Character, Character>(new SingleCharacterParser('-'));
    private final Parser<Character, Collection<Character>> digits = new ZeroOrMoreParser<Character, Character>(new CharInStringParser("0123456789"));
    @SuppressWarnings("unchecked")
    private final Parser<Character, String> whole = new OrParser<Character, String>(
        new Parser<Character, String>(){
            public String parse(ParseContext<Character> context) throws ParseException {
                Character c;
                try {
                    c = context.read();
                } catch (StreamException e) {
                    throw new ParseException("Exception reading underlying stream", e);
                }
                if(c != '0'){
                    throw new ParseException("Expected zero, got " + c);
                }
                return "0";
            }
        },
        new Parser<Character, String>(){
            public String parse(ParseContext<Character> context) throws ParseException {
                Character c;
                try {
                    c = context.read();
                
                    if(c < '1' || c > '9'){
                        throw new ParseException("Expected digit, got " + c);
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(c);
                    sb.append(digits.parse(context));
                    return sb.toString();
                } catch (StreamException e) {
                    throw new ParseException("Exception reading underlying stream", e);
                }
            }
        }
    );
    private final Parser<Character, Optional<String>> optionalFraction = new ZeroOrOneParser<Character, String>(
        new Parser<Character, String>(){
            public String parse(ParseContext<Character> context) throws ParseException {
                Character c;
                try {
                    c = context.read();
                } catch (StreamException e) {
                    throw new ParseException("Exception reading underlying stream", e);
                }
                if(c != '.'){
                    throw new ParseException("Expected '.', got " + c);
                }
                return "." + digits.parse(context);
            }
        }
    );
    private final Parser<Character, Optional<String>> optionalExponent = new ZeroOrOneParser<Character, String>(
        new Parser<Character, String>(){
            private final Parser<Character, Character> e = new CharInStringParser("eE");
            private final Parser<Character, Optional<Character>> optionalSign = new ZeroOrOneParser<Character, Character>(new CharInStringParser("+-"));
            public String parse(ParseContext<Character> context) throws ParseException {
                StringBuilder sb = new StringBuilder();
                sb.append(e.parse(context));
                sb.append(optionalSign.parse(context));
                sb.append(digits.parse(context));
                return sb.toString();
            }
        }
    );

    public SNumber parse(ParseContext<Character> context) throws ParseException {
        StringBuilder sb = new StringBuilder();
        Optional<Character> minus = optionalMinus.parse(context);
        if(minus.isSome()){
            sb.append(minus.get());
        }
        sb.append(whole.parse(context));
        Optional<String> fraction = optionalFraction.parse(context);
        if(fraction.isSome()){
            sb.append(fraction.get());
        }
        Optional<String> exponent = optionalExponent.parse(context);
        if(exponent.isSome()){
            sb.append(exponent.get());
        }
        return new SNumber(new BigDecimal(sb.toString()));
    }
    
}
