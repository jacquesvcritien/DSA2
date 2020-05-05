import exceptions.SyntaxErrorException;

import java.util.ArrayList;
import java.util.Set;

public class ParserExecutor {
    public static void main(String args[]) throws SyntaxErrorException {
        //init stringbuilder
        StringBuilder toParse = new StringBuilder();

        //get args
        for(int i=0; i < args.length; i++)
            toParse.append(args[i]);

        //create a new parser
        Parser parser = new Parser();

        //parser the args
        Set<Clause> clauses = parser.parseString(toParse.toString());
        //call dpll
        DPLL.DPLL(clauses);
    }
}
