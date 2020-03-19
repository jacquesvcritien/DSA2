import exceptions.SyntaxErrorException;

import java.util.ArrayList;
import java.util.Set;

public class ParserExecutor {
    public static void main(String args[]) throws SyntaxErrorException {
        StringBuilder toParse = new StringBuilder();

        for(int i=0; i < args.length; i++)
            toParse.append(args[i]);

        Parser parser = new Parser();


        Set<Clause> clauses = parser.parseString(toParse.toString());


//        clauses = Parser.removeTriviallySat(clauses);

        System.out.println("is unsat: "+Parser.checkTriviallyUnSat(clauses));
        Parser.printParsed(clauses);
    }
}
