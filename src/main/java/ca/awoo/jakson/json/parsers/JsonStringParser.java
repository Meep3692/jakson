package ca.awoo.jakson.json.parsers;

import ca.awoo.jakson.svalue.SString;
import ca.awoo.praser.ParseContext;
import ca.awoo.praser.ParseException;
import ca.awoo.praser.Parser;
import ca.awoo.praser.StreamException;

public class JsonStringParser implements Parser<Character, SString> {

    private static final Parser<Character, Integer> fourhex = new Parser<Character, Integer>(){
        public Integer parse(ParseContext<Character> context) throws ParseException {
            //Read 4 hex digits
            int i = 0;
            for(int j = 0; j < 4; j++){
                Character c;
                try {
                    c = context.read();
                } catch (StreamException e) {
                    throw new ParseException("Exception reading underlying stream", e);
                }
                if(c >= '0' && c <= '9'){
                    i = (i << 4) + (c - '0');
                }else if(c >= 'a' && c <= 'f'){
                    i = (i << 4) + (c - 'a' + 10);
                }else if(c >= 'A' && c <= 'F'){
                    i = (i << 4) + (c - 'A' + 10);
                }else{
                    throw new ParseException("Expected hex digit, got " + c);
                }
            }
            return i;
        }
    };

    public SString parse(ParseContext<Character> context) throws ParseException {
        try{
            Character c;
            c = context.read();
            if(c != '"'){
                throw new ParseException("Expected \" at start of string, got " + c);
            }
            StringBuilder sb = new StringBuilder();
            while((c = context.read()) != '"'){
                if(c == '\\'){
                    c = context.read();
                    switch(c){
                        case '"':
                            sb.append('"');
                            break;
                        case '\\':
                            sb.append('\\');
                            break;
                        case '/':
                            sb.append('/');
                            break;
                        case 'b':
                            sb.append('\b');
                            break;
                        case 'f':
                            sb.append('\f');
                            break;
                        case 'n':
                            sb.append('n');
                            break;
                        case 'r':
                            sb.append('\r');
                            break;
                        case 't':
                            sb.append('\t');
                            break;
                        case 'u':
                            sb.append((char)fourhex.parse(context).intValue());
                            break;
                        default:
                            throw new ParseException("Expected escape sequence, got " + c);
                    }
                }else{
                    sb.append(c);
                }
            }
            return new SString(sb.toString());
        }catch(StreamException e){
            throw new ParseException("Exception reading underlying stream", e);
        }
    }
}
