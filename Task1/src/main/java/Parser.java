
import exceptions.SyntaxErrorException;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Parser {

    /**
     * Method to parse a string
     * @return An ArrayList of Clauses if it is correct
     * @throws SyntaxErrorException if the string is not of the correct format
     */
    public static ArrayList<Clause> parseString(String toParse) throws SyntaxErrorException {
        ArrayList<Clause> clauses = new ArrayList<Clause>();

        if(toParse.length() ==0)
            throw new SyntaxErrorException("String cannot be empty");

        //check for first and last parentheses
        if(!toParse.startsWith("(") || !toParse.endsWith(")"))
            throw new SyntaxErrorException("Input must start with with a ( and ends with a )");

        //remove spaces
        toParse = toParse.replaceAll(" ","");

        //remove first and last parentheses
        toParse = toParse.substring(1,toParse.length()-1);
        //get clauses by dividing on every ")("
        StringTokenizer clauseTokenizer = new StringTokenizer(toParse, ")(");
        //initialise literal tokenizer
        StringTokenizer literalTokenizer;

        //for every clause token
        while(clauseTokenizer.hasMoreTokens())
        {
            //get that clause as string
            String clauseString = clauseTokenizer.nextToken();
            //get literals by dividing on every ","
            literalTokenizer = new StringTokenizer(clauseString, ",");
            Clause clause = new Clause();
            //for every literal
            while(literalTokenizer.hasMoreTokens())
            {
                String stringLiteral = literalTokenizer.nextToken();
                Literal literal;

                //check if negated, add literal with second letter and true, if not, add literal with first letter and false
                literal = new Literal(stringLiteral);
                //add literal to clause
                clause.addLiteral(literal);
            }

            clauses.add(clause);
        }

        return clauses;
    }

    public static void printParsed(ArrayList<Clause> clauses)
    {
        for(int i=0; i <clauses.size(); i++)
        {
            System.out.println("Clause "+(i+1)+":");
            Clause clause = clauses.get(i);
            for(int j=0; j<clause.getLiterals().size(); j++)
            {
                Literal literal = clause.getLiterals().get(j);
                String negated = (literal.isNegated()) ? "¬" : "";
                System.out.println("Literal "+(i+1)+"."+j+": "+negated+clause.getLiterals().get(j).symbol.toString());
            }

            System.out.println("");
        }
    }
}
