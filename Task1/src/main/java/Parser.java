
import exceptions.SyntaxErrorException;

import java.lang.reflect.Array;
import java.util.*;

public class Parser {

    /**
     * Method to parse a string
     * @return An ArrayList of Clauses if it is correct
     * @throws SyntaxErrorException if the string is not of the correct format
     */
    public static Set<Clause> parseString(String toParse) throws SyntaxErrorException {
        Set<Clause> clauses = new HashSet<Clause>();

        //if string is empty
        if(toParse.length() ==0)
            throw new SyntaxErrorException("String cannot be empty");

        //remove spaces
        toParse = toParse.replaceAll(" ","");

        //check for first and last parentheses
        if(!toParse.startsWith("(") || !toParse.endsWith(")"))
            throw new SyntaxErrorException("Input must start with with a ( and ends with a )");


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
                //get next token
                String stringLiteral = literalTokenizer.nextToken();
                Literal literal;

                //check if negated, add literal with second letter and true, if not, add literal with first letter and false
                literal = new Literal(stringLiteral);

                //if assignments does not contain the symbol, insert it
                if(!DPLL.assignments.containsKey(literal.getSymbol()))
                    DPLL.assignments.put(literal.getSymbol(), Assignment.DONTCARE);

                //add literal to clause
                clause.addLiteral(literal);
            }

            //add clause to clauses
            clauses.add(clause);
        }

        return clauses;
    }


    /**
     * Method to print the clauses parsed
     * @param clauses a list of clauses
     */
    public static void printParsed(Set<Clause> clauses)
    {
        //create iterator
        Iterator<Clause> clauseItr = clauses.iterator();

        int counter = 1;
        //loop through each clause
        while(clauseItr.hasNext())
        {
            //print clause number
            System.out.println("Clause "+(counter)+":");
            //get clause
            Clause clause = clauseItr.next();
            //for each literal
            for(int j=0; j<clause.getLiterals().size(); j++)
            {
                Literal literal = clause.getLiterals().get(j);
                String negated = (literal.isNegated()) ? "¬" : "";
                System.out.println("Literal "+(counter)+"."+(j+1)+": "+negated+clause.getLiterals().get(j).getSymbol().toString());
            }

            counter++;
            System.out.println("");
        }
    }


}
