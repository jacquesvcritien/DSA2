
import exceptions.SyntaxErrorException;

import java.lang.reflect.Array;
import java.util.*;

public class Parser {

    static HashMap<Symbol, Assignment> assignments = new HashMap<Symbol, Assignment>();

//    /**
//     * Method to parse a string
//     * @return An ArrayList of Clauses if it is correct
//     * @throws SyntaxErrorException if the string is not of the correct format
//     */
//    public static ArrayList<Clause> parseString(String toParse) throws SyntaxErrorException {
//        ArrayList<Clause> clauses = new ArrayList<Clause>();
//
//        if(toParse.length() ==0)
//            throw new SyntaxErrorException("String cannot be empty");
//
//        //remove spaces
//        toParse = toParse.replaceAll(" ","");
//
//        //check for first and last parentheses
//        if(!toParse.startsWith("(") || !toParse.endsWith(")"))
//            throw new SyntaxErrorException("Input must start with with a ( and ends with a )");
//
//
//        //remove first and last parentheses
//        toParse = toParse.substring(1,toParse.length()-1);
//        //get clauses by dividing on every ")("
//        StringTokenizer clauseTokenizer = new StringTokenizer(toParse, ")(");
//        //initialise literal tokenizer
//        StringTokenizer literalTokenizer;
//
//        //for every clause token
//        while(clauseTokenizer.hasMoreTokens())
//        {
//            //get that clause as string
//            String clauseString = clauseTokenizer.nextToken();
//            //get literals by dividing on every ","
//            literalTokenizer = new StringTokenizer(clauseString, ",");
//            Clause clause = new Clause();
//            //for every literal
//            while(literalTokenizer.hasMoreTokens())
//            {
//                String stringLiteral = literalTokenizer.nextToken();
//                Literal literal;
//
//                //check if negated, add literal with second letter and true, if not, add literal with first letter and false
//                literal = new Literal(stringLiteral);
//                //add literal to clause
//                clause.addLiteral(literal);
//            }
//
//            clauses.add(clause);
//        }
//
//        return clauses;
//    }

    /**
     * Method to parse a string
     * @return An ArrayList of Clauses if it is correct
     * @throws SyntaxErrorException if the string is not of the correct format
     */
    public static Set<Clause> parseString(String toParse) throws SyntaxErrorException {
        Set<Clause> clauses = new HashSet<Clause>();

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
                String stringLiteral = literalTokenizer.nextToken();
                Literal literal;

                //check if negated, add literal with second letter and true, if not, add literal with first letter and false
                literal = new Literal(stringLiteral);

                if(!assignments.containsKey(literal.symbol))
                    assignments.put(literal.symbol, Assignment.DONTCARE);

                //add literal to clause
                clause.addLiteral(literal);
            }

            clauses.add(clause);
        }

        return clauses;
    }

    public static ArrayList<Clause> getUnitClauses(ArrayList<Clause> clauses)
    {
        //init list of clauses to return
        ArrayList<Clause> toReturn = new ArrayList<Clause>();
        //loop through each clause
        for(int i=0; i < clauses.size(); i++)
        {
            //get current clause
            Clause currentClause = clauses.get(i);
            //if the clause contains 1 literal, add to the list to return
            if(currentClause.getLiterals().size() == 1)
                toReturn.add(currentClause);
        }

        return toReturn;
    }

    /**
     * Method to get the next unit clause
     * @param clauses list of clauses
     * @return unit clause
     */
    public static Clause getUnitClause(ArrayList<Clause> clauses)
    {
        //loop through each clause
        for(int i=0; i < clauses.size(); i++)
        {
            //get current clause
            Clause currentClause = clauses.get(i);
            ArrayList<Literal> clauseLiterals = currentClause.getLiterals();

            //if the clause contains 1 literal, return it
            if(clauseLiterals.size() == 1)
            {
                Literal lt = currentClause.getLiterals().get(0);
                Clause toReturn = new Clause();
                Literal literal = lt.clone();
                toReturn.addLiteral(literal);

                return toReturn;
            }
        }

        return null;
    }

    /**
     * Method to get the next unit clause
     * @param clauses list of clauses
     * @return unit clause
     */
    public static Clause getUnitClause(Set<Clause> clauses)
    {
        Iterator<Clause> clauseItr = clauses.iterator();

        //loop through each clause
        while(clauseItr.hasNext())
        {
            //get current clause
            Clause currentClause = clauseItr.next();
            ArrayList<Literal> clauseLiterals = currentClause.getLiterals();

            //if the clause contains 1 literal, return it
            if(clauseLiterals.size() == 1)
            {
                Literal lt = currentClause.getLiterals().get(0);
                Clause toReturn = new Clause();
                Literal literal = lt.clone();
                toReturn.addLiteral(literal);

                return toReturn;
            }
        }

        return null;
    }

    /**
     * Method to get the next pure literal
     * @param clauses list of clauses
     * @return the next pure literal
     */
    public static Literal getPureLiteralV2(ArrayList<Clause> clauses)
    {
        //boolean flag for searching
        boolean searching;
        //loop through each clause
        for(int i=0; i < clauses.size(); i++)
        {
            //set searching to true
            searching = true;
            //get current clause
            Clause currentClause = clauses.get(i);
            //get current clause's literals
            ArrayList<Literal> clauseLiterals = currentClause.getLiterals();

            //loop through literals
            for(int j=0; j < clauseLiterals.size(); j++)
            {
                //get current literal
                Literal currentLiteral = clauseLiterals.get(j);

                //loop again through clauses
                for(int k = 0; k < clauses.size() && searching; k++)
                {
                    //get the second current clause
                    Clause currentClause2 = clauses.get(k);
                    //get the second current clause's literals
                    ArrayList<Literal> clause2Literals = currentClause2.getLiterals();

                    //loop through the second current clause's literals
                    for(int l=0; l < clause2Literals.size(); l++)
                    {
                        //get the second current literal
                        Literal currentLiteral2 = clause2Literals.get(l);

                        //if a literal with the same symbol but inverted negation symbol, quit searching and break to check next literal
                        if(currentLiteral.symbol.equals(currentLiteral2.symbol) && (currentLiteral.negated ^ currentLiteral2.negated))
                        {
                            searching = false;
                            break;
                        }
                    }
                }

                //if there were no opposite literals
                if(searching)
                    return currentLiteral.clone();
            }
        }
        //if no pures
        return null;
    }

    /**
     * Method to get the next pure literal
     * @param clauses list of clauses
     * @return the next pure literal
     */
    public static Literal getPureLiteral(Set<Clause> clauses)
    {
        boolean searching;
        ArrayList<Literal> literalsInClauses = new ArrayList<Literal>();
        //add all literals
        for(Clause clause: clauses)
            literalsInClauses.addAll(clause.getLiterals());

        for(int i=0; i<literalsInClauses.size(); i++)
        {
            searching = true;
            //get current literal
            Literal currentLiteral1 = literalsInClauses.get(i);

            for(int j=0; j<literalsInClauses.size() && searching; j++)
            {
                //get second current literal
                Literal currentLiteral2 = literalsInClauses.get(j);

                //if a literal with the same symbol but inverted negation symbol, quit searching and break to check next literal
                if(currentLiteral1.symbol.equals(currentLiteral2.symbol) && (currentLiteral1.negated ^ currentLiteral2.negated))
                {
                    searching = false;
                    break;
                }
            }
            //if there were no opposite literals
            if(searching == true)
                return currentLiteral1.clone();
        }

        return null;
    }
    /**
     * Method to get the next pure literal
     * @param clauses list of clauses
     * @return the next pure literal
     */
    public static Literal getPureLiteralV2(Set<Clause> clauses)
    {
        //boolean flag for searching
        boolean searching;


        Iterator<Clause> clauseItr = clauses.iterator();

        //loop through each clause
        while(clauseItr.hasNext())
        {
            //set searching to true
            searching = true;
            //get current clause
            Clause currentClause = clauseItr.next();
            //get current clause's literals
            ArrayList<Literal> clauseLiterals = currentClause.getLiterals();

            //loop through literals
            for(int j=0; j < clauseLiterals.size(); j++)
            {
                //get current literal
                Literal currentLiteral = clauseLiterals.get(j);

                Iterator<Clause> clauseItr2 = clauses.iterator();

                //loop through each clause
                while(clauseItr2.hasNext())
                {
                    //get the second current clause
                    Clause currentClause2 = clauseItr2.next();
                    //get the second current clause's literals
                    ArrayList<Literal> clause2Literals = currentClause2.getLiterals();

                    //loop through the second current clause's literals
                    for(int l=0; l < clause2Literals.size(); l++)
                    {
                        //get the second current literal
                        Literal currentLiteral2 = clause2Literals.get(l);

                        //if a literal with the same symbol but inverted negation symbol, quit searching and break to check next literal
                        if(currentLiteral.symbol.equals(currentLiteral2.symbol) && (currentLiteral.negated ^ currentLiteral2.negated))
                        {
                            searching = false;
                            break;
                        }
                    }
                }

                //if there were no opposite literals
                if(searching)
                    return currentLiteral.clone();
            }
        }
        //if no pures
        return null;
    }

    /**
     * Method to apply pure literal
     * @param pure pure literal
     * @param clauses list of clauses
     * @return list of clauses after pure literal rule is applied
     */
    public static ArrayList<Clause> applyPureLiteralRule(Literal pure, ArrayList<Clause> clauses)
    {
        //loop through all clauses
        for(int i=0; i < clauses.size(); i++)
        {
            Clause currentClause = clauses.get(i);
            //get clause's literals
            ArrayList<Literal> clauseLiterals = currentClause.getLiterals();
            //loop through all literals
            for(Literal literal: clauseLiterals)
                //if literal is equal to pure, remove the clause
                if(literal.equals(pure))
                {
                    clauses.remove(currentClause);
                    i--;
                    break;
                }
        }
        return clauses;
    }

    /**
     * Method to apply pure literal
     * @param pure pure literal
     * @param clauses list of clauses
     * @return list of clauses after pure literal rule is applied
     */
    public static Set<Clause> applyPureLiteralRule(Literal pure, Set<Clause> clauses)
    {
        Iterator<Clause> clauseItr = clauses.iterator();

        //loop through each clause
        while(clauseItr.hasNext())
        {
            Clause currentClause = clauseItr.next();
            //get clause's literals
            ArrayList<Literal> clauseLiterals = currentClause.getLiterals();
            //loop through all literals
            for(Literal literal: clauseLiterals)
                //if literal is equal to pure, remove the clause
                if(literal.equals(pure))
                {
                    clauseItr.remove();
                    break;
                }
        }
        return clauses;
    }

    /**
     * Method which performs the 1 literal rule exhaustively
     * @param clauses list of clauses
     * @return a list of clauses after performing the 1 literal rule
     */
    public static Set<Clause> exhaustivelyApply1LiteralRule(Clause clause, Set<Clause> clauses)
    {
        Iterator<Clause> clauseItr = clauses.iterator();

        //loop through each clause
        while(clauseItr.hasNext())
        {
            //get current clause
            Clause currentClause = clauseItr.next();
            //get unit clause's literal
            Literal clauseLiteral = clause.literals.get(0);

            //get current clause's literals
            ArrayList<Literal> currentClauseLiterals = currentClause.getLiterals();

            //loop through current clause's literals
            for (int j = 0; j < currentClauseLiterals.size(); j++)
            {
                //get current literal
                Literal currentLiteral = currentClauseLiterals.get(j);

                //negate unit clause's literal
                clauseLiteral.negated = !clauseLiteral.negated;

                //if unit clause's literal is equal to the current literal, remove the literal from the clause
                if (clauseLiteral.equals(currentLiteral)) {
                    currentClauseLiterals.remove(j);
                    j--;
                }

                //negate unit clause's literal (get original)
                clauseLiteral.negated = !clauseLiteral.negated;
                //if the unit clause's literal is equal to the current literal, remove clause
                if (clauseLiteral.equals(currentLiteral)) {
                    clauseItr.remove();
                    break;
                }
            }
        }
        return clauses;
    }

    /**
     * Method which performs the 1 literal rule exhaustively
     * @param clauses list of clauses
     * @return a list of clauses after performing the 1 literal rule
     */
    public static ArrayList<Clause> exhaustivelyApply1LiteralRule(Clause clause, ArrayList<Clause> clauses)
    {
        //loop through all clauses
        for(int i=0; i < clauses.size(); i++)
        {
            //get current clause
            Clause currentClause = clauses.get(i);
            //get unit clause's literal
            Literal clauseLiteral = clause.literals.get(0);

            //get current clause's literals
            ArrayList<Literal> currentClauseLiterals = currentClause.getLiterals();

            //loop through current clause's literals
            for (int j = 0; j < currentClauseLiterals.size(); j++)
            {
                //get current literal
                Literal currentLiteral = currentClauseLiterals.get(j);

                //negate unit clause's literal
                clauseLiteral.negated = !clauseLiteral.negated;

                //if unit clause's literal is equal to the current literal, remove the literal from the clause
                if (clauseLiteral.equals(currentLiteral)) {
                    currentClauseLiterals.remove(j);
                    j--;
                }

                //negate unit clause's literal (get original)
                clauseLiteral.negated = !clauseLiteral.negated;
                //if the unit clause's literal is equal to the current literal, remove clause
                if (clauseLiteral.equals(currentLiteral)) {
                    clauses.remove(i);
                    i--;
                    break;
                }
            }
        }
        return clauses;
    }

    /**
     * Method to print the clauses parsed
     * @param clauses a list of clauses
     */
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
     * Method to print the clauses parsed
     * @param clauses a list of clauses
     */
    public static void printParsed(Set<Clause> clauses)
    {
        Iterator<Clause> clauseItr = clauses.iterator();

        int counter = 1;
        //loop through each clause
        while(clauseItr.hasNext())
        {
            System.out.println("Clause "+(counter+1)+":");
            Clause clause = clauseItr.next();
            for(int j=0; j<clause.getLiterals().size(); j++)
            {
                Literal literal = clause.getLiterals().get(j);
                String negated = (literal.isNegated()) ? "¬" : "";
                System.out.println("Literal "+(counter+1)+"."+j+": "+negated+clause.getLiterals().get(j).symbol.toString());
            }

            counter++;
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

            for(int j = i+1; j < clauses.size(); j++)
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
     * Method which checks if there are any trivially unsatisfiable clauses from a list of clauses
     * form should be (x)(!x)
     * @param clauses clauses to check
     * @return true if a trivially unsatisfiable clause is found
     */
    public static boolean checkTriviallyUnSat(Set<Clause> clauses)
    {
        Iterator<Clause> clauseItr = clauses.iterator();
        //loop through each clause
        while(clauseItr.hasNext())
        {
            Clause first = clauseItr.next();

            //if the first clause has more or less than 1 literal
            if(first.getLiterals().size() != 1)
                continue;

            Iterator<Clause> clauseItr2 = clauses.iterator();
            //loop through each clause
            while(clauseItr2.hasNext())
            {
                Clause second = clauseItr2.next();
                //if the second clause has more or less than 1 literal
                if(second.getLiterals().size() != 1)
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
     * Method which removes trivially satisfiable clauses from a list of clauses
     * form should be (x, !x)
     * @param clauses clauses to check
     * @return list of clauses without trivially satisfiable clauses
     */
    public static Set<Clause> removeTriviallySat(Set<Clause> clauses)
    {
        //flag to check if removed
        boolean removed = false;
        Iterator<Clause> clauseItr = clauses.iterator();
        //loop through each clause
        while(clauseItr.hasNext())
        {
            //set rmeoved to false
            removed = false;
            Clause clause = clauseItr.next();
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
                        clauseItr.remove();
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

    /**
     * Method to check if there are any empty clauses
     * @param clauses clauses to check
     * @return boolean whether there are empty clauses
     */
    public static boolean checkEmptyClause(ArrayList<Clause> clauses)
    {
        //loop through all clauses
        for(int i=0; i <clauses.size();i++)
            //if there is a clause with no literals, return true
            if(clauses.get(i).getLiterals().isEmpty())
                return true;

        return false;
    }

    /**
     * Method to check if there are any empty clauses
     * @param clauses clauses to check
     * @return boolean whether there are empty clauses
     */
    public static boolean checkEmptyClause(Set<Clause> clauses)
    {
        //loop through all clauses
        Iterator<Clause> clauseItr = clauses.iterator();
        //loop through each clause
        while(clauseItr.hasNext()) 
        {
            Clause currentClause = clauseItr.next();
            //if there is a clause with no literals, return true
            if (currentClause.getLiterals().isEmpty())
                return true;
        }

        return false;
    }

    /**
     * Method to choose literal accoring to the most frequent literal
     * @param clauses list of clauses
     * @return most frequent literal
     */
    public static Literal chooseLiteral(Set<Clause> clauses) {
        //init bins
        HashMap<Symbol, Integer> bins = new HashMap<Symbol, Integer>();

        ArrayList<Literal> literalsInClauses = new ArrayList<Literal>();
        //add all literals
        for (Clause clause : clauses)
            literalsInClauses.addAll(clause.getLiterals());

        //loop through all literals
        for (Literal literal : literalsInClauses) {
            //if bins has literal symbol, increment value
            if (bins.containsKey(literal.symbol)) {
                Integer currentVal = bins.get(literal.symbol);
                bins.put(literal.symbol, currentVal + 1);
            }
            //else create key in bin
            else
                bins.put(literal.symbol, 1);
        }

        //get maximum bin
        Map.Entry<Symbol, Integer> max = null;

        //loop through bins
        for (Map.Entry<Symbol, Integer> entry : bins.entrySet()) {
            if (max == null || entry.getValue() > max.getValue()) {
                max = entry;
            }
        }

        if (max != null)
        {
            //create literal to return with symbol

            Literal toReturn = new Literal();
            toReturn.symbol = max.getKey();
            return toReturn;
        }
        return null;
    }

    public static boolean DPLL(ArrayList<Clause> clauses)
    {
        //remove trivially satisfiable clauses
        clauses = removeTriviallySat(clauses);

        //If a clause is trivially unsatisfiable return false
        if(checkTriviallyUnSat(clauses))
            return false;

        Parser.printParsed(clauses);

        //if clauses contains an empty clause, return false
        if(checkEmptyClause(clauses))
            return false;

        //if clauses is an empty set
        if(clauses.isEmpty())
            return true;

        //get unit clauses
        Clause unitClause = getUnitClause(clauses);

        while(unitClause != null)
        {
            clauses = exhaustivelyApply1LiteralRule(unitClause, clauses);
            unitClause = getUnitClause(clauses);
        }

        System.out.println("After 1 literal");
        Parser.printParsed(clauses);
        Literal pureLiteral = getPureLiteralV2(clauses);

        while(pureLiteral != null)
        {
            clauses = applyPureLiteralRule(pureLiteral, clauses);
            pureLiteral = getPureLiteralV2(clauses);
        }

        System.out.println("After pure literal");

        Parser.printParsed(clauses);

        System.out.println("reached END");
        return false;
    }

    public static boolean DPLL(Set<Clause> clauses)
    {
        //remove trivially satisfiable clauses
        clauses = removeTriviallySat(clauses);

        //If a clause is trivially unsatisfiable return false
        if(checkTriviallyUnSat(clauses))
            return false;

        Parser.printParsed(clauses);

        //if clauses contains an empty clause, return false
        if(checkEmptyClause(clauses))
        {
            System.out.println("UNSAT");
            return false;

        }

        //if clauses is an empty set
        if(clauses.isEmpty())
        {
            for(Symbol symbol: assignments.keySet())
            {
                String key = symbol.toString();
                String value = assignments.get(symbol).toString();
                System.out.println(key + " " + value);
            }
            return true;
        }

        //get unit clauses
        Clause unitClause = getUnitClause(clauses);

        while(unitClause != null)
        {
            assignments.put(unitClause.getLiterals().get(0).symbol, (unitClause.getLiterals().get(0).negated) ? Assignment.FALSE : Assignment.TRUE);
            clauses = exhaustivelyApply1LiteralRule(unitClause, clauses);
            unitClause = getUnitClause(clauses);
        }

        System.out.println("After 1 literal");
        Parser.printParsed(clauses);
        Literal pureLiteral = getPureLiteral(clauses);

        while(pureLiteral != null)
        {
            assignments.put(pureLiteral.symbol, (pureLiteral.negated) ? Assignment.FALSE : Assignment.TRUE);
            clauses = applyPureLiteralRule(pureLiteral, clauses);
            pureLiteral = getPureLiteral(clauses);
        }

        Literal literal = chooseLiteral(clauses);

        if(literal != null) {
            Clause clause = new Clause();
            clause.addLiteral(literal);
            clauses.add(clause);
        }
        return DPLL(clauses);
    }


}
