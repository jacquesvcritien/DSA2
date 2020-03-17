
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

    /**
     * Method which checks if there are any trivially unsatisfiable clauses from a list of clauses
     * form should be (x)(!x)
     * @param clauses clauses to check
     * @return true if a trivially unsatisfiable clause is found
     */
    public static boolean checkTriviallyUnSat(ArrayList<Clause> clauses)
    {
        for(int i = 0; i < clauses.size(); i++)
        {
            Clause first = clauses.get(i);

            //if the first clause has more than 1 literal
            if(first.getLiterals().size() > 1)
                continue;

            for(int j = 0; j < clauses.size(); j++)
            {
                Clause second = clauses.get(j);
                //if the second clause has more than 1 literal
                if(second.getLiterals().size() > 1)
                    continue;

                //get first clause's literal
                Literal firstClauseLiteral = first.getLiterals().get(0);
                //get second clause's literal
                Literal secondClauseLiteral = second.getLiterals().get(0);

                //if both clauses' literal have the same symbol but they're negation is different (xor)
                if(firstClauseLiteral.getSymbol().equals(secondClauseLiteral.getSymbol()) && (firstClauseLiteral.isNegated() ^ secondClauseLiteral.isNegated()))
                    return true;
            }

        }
        return false;
    }

    /**
     * Method which removes trivially satisfiable clauses from a list of clauses
     * form should be (x, !x)
     * @param clauses clauses to check
     * @return list of clauses without trivially satisfiable clauses
     */
    public static ArrayList<Clause> removeTriviallySat(ArrayList<Clause> clauses)
    {
        //flag to check if removed
        boolean removed = false;
        for(int i = 0; i < clauses.size(); i++)
        {
            //set rmeoved to false
            removed = false;
            Clause clause = clauses.get(i);
            //get clause literals
            ArrayList<Literal> literals = clause.getLiterals();

            //nested loop for clause's literals
            for(int j =0; j < literals.size() && !removed; j++)
            {
                //get first literal
                Literal first = literals.get(j);
                for(int k =0; k < literals.size(); k++)
                {
                    //get second literal
                    Literal second = literals.get(k);
                    //if both clause's literals have the same symbol but they're negation is different (xor)
                    if(first.getSymbol().equals(second.getSymbol()) && (first.isNegated() ^ second.isNegated()))
                    {
                        //remove clause
                        clauses.remove(i);
                        //decrement i
                        i--;
                        //set flag
                        removed = true;
                        //break and check next clause
                        break;
                    }
                }
            }

        }
        return clauses;
    }

    /**
     * Method to get first literal from list of clauses
     * @param clauses list of clauses
     * @return first literal
     */
    public Literal getFirstLiteral(ArrayList<Clause> clauses)
    {
        //if size is bigger than 0
        if(clauses.size() > 0) {
            //get clause's literals
            ArrayList<Literal> literals = clauses.get(0).getLiterals();
            //if not empty literals
            if (literals.size() > 0)
                //return first literal
                return literals.get(0);
        }

        return null;
    }

    public Literal getFirstLiteralNotNegated(ArrayList<Clause> clauses)
    {

        for(int i = 0; i  < clauses.size(); i++)
        {
            Clause clause = clauses.get(i);
            ArrayList<Literal> literals = clause.getLiterals();
            for(int j =0; j < literals.size(); j++)
            {
                Literal literal = literals.get(j);
                if(!literal.isNegated())
                    return literal;
            }
        }

        return null;
    }
}
