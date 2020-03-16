import exceptions.SyntaxErrorException;

import java.util.ArrayList;

public class ParserExecutor {
    public static void main(String args[]) throws SyntaxErrorException {
        StringBuilder toParse = new StringBuilder();

        for(int i=0; i < args.length; i++)
            toParse.append(args[i]);

        Parser parser = new Parser();
        ArrayList<Clause> clauses = parser.parseString(toParse.toString());

        Parser.printParsed(clauses);
    }
}
