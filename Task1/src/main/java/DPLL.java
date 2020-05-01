
import exceptions.SyntaxErrorException;

import java.util.*;

public class DPLL {

    //hashmap to hold assignments
    static HashMap<Symbol, Assignment> assignments = new HashMap<Symbol, Assignment>();

    /**
     * Method to get the next unit clause
     * @param clauses list of clauses
     * @return unit clause
     */
    public static Clause getUnitClause(Set<Clause> clauses)
    {
        //create an iterator
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

            for(int j=0; j<literalsInClauses.size(); j++)
            {
                //get second current literal
                Literal currentLiteral2 = literalsInClauses.get(j);

                //if a literal with the same symbol but inverted negation symbol, quit searching and break to check next literal
                if(currentLiteral1.getSymbol().equals(currentLiteral2.getSymbol()) && (currentLiteral1.isNegated() ^ currentLiteral2.isNegated()))
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
                clauseLiteral.setNegated(!clauseLiteral.isNegated());

                //if unit clause's literal is equal to the current literal, remove the literal from the clause
                if (clauseLiteral.equals(currentLiteral)) {
                    currentClauseLiterals.remove(j);
                    j--;
                }

                //negate unit clause's literal (get original)
                clauseLiteral.setNegated(!clauseLiteral.isNegated());
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

                //if both clauses' literal have the same symbol but their negation is different (xor)
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
    public static Set<Clause> removeTriviallySat(Set<Clause> clauses)
    {
        //flag to check if removed
        boolean removed = false;
        Iterator<Clause> clauseItr = clauses.iterator();
        //loop through each clause
        while(clauseItr.hasNext())
        {
            //set removed to false
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
     * Method to choose literal according to the most frequent literal
     * @param clauses list of clauses
     * @return most frequent literal
     */
    public static Literal chooseLiteral(Set<Clause> clauses) {
        //init bins
        HashMap<Literal, Integer> bins = new HashMap<Literal, Integer>();

        ArrayList<Literal> literalsInClauses = new ArrayList<Literal>();
        //add all literals
        for (Clause clause : clauses)
            literalsInClauses.addAll(clause.getLiterals());

        //loop through all literals
        for (Literal literal : literalsInClauses) {
            //if bins has literal symbol, increment value
            if (bins.containsKey(literal)) {
                Integer currentVal = bins.get(literal);
                bins.put(literal, currentVal + 1);
            }
            //else create key in bin
            else
                bins.put(literal, 1);
        }

        //get maximum bin
        Map.Entry<Literal, Integer> max = null;

        //loop through bins
        for (Map.Entry<Literal, Integer> entry : bins.entrySet()) {
            if (max == null || entry.getValue() > max.getValue()) {
                max = entry;
            }
        }

        if (max != null)
        {
            //create literal to return with symbol

            Literal toReturn = new Literal();
            toReturn.setSymbol(max.getKey().getSymbol());
            return toReturn;
        }
        return null;
    }

    public static boolean DPLL(Set<Clause> clauses)
    {
        //remove trivially satisfiable clauses
        clauses = removeTriviallySat(clauses);

        //If a clause is trivially unsatisfiable return false
        if(checkTriviallyUnSat(clauses))
        {
            System.out.println("UNSAT");
            return false;
        }

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

        //loop though each unit clause
        while(unitClause != null)
        {
            //add unit clause to assignments
            assignments.put(unitClause.getLiterals().get(0).getSymbol(), (unitClause.getLiterals().get(0).isNegated()) ? Assignment.FALSE : Assignment.TRUE);
            //apply 1 literal rule exhaustively
            clauses = exhaustivelyApply1LiteralRule(unitClause, clauses);
            //get the next unit clause
            unitClause = getUnitClause(clauses);
        }

        //get pure literal
        Literal pureLiteral = getPureLiteral(clauses);

        //loop through each pure literal
        while(pureLiteral != null)
        {
            //add to assignments
            assignments.put(pureLiteral.getSymbol(), (pureLiteral.isNegated()) ? Assignment.FALSE : Assignment.TRUE);
            //apply 1 literal rule exhaustively
            clauses = applyPureLiteralRule(pureLiteral, clauses);
            //get the next pure literal
            pureLiteral = getPureLiteral(clauses);
        }

        //choose literal
        Literal literal = chooseLiteral(clauses);

        //copy clauses
//        Set<Clause> clauses2 = new HashSet<Clause>();
//        clauses2.addAll(clauses);

        //if literal is not null, add literal to clauses
        if(literal != null) {
            //add literal to 1st set
            Clause clause = new Clause();
            clause.addLiteral(literal);
            clauses.add(clause);

//            //add another literal to the other set but negated
//            Clause clause2 = new Clause();
//            clause2.addLiteral(new Literal(literal.symbol, true));
//            clauses2.add(clause2);
        }

        //recursive call
//        boolean val = DPLL(clauses);
//        System.out.println("VAL1: "+val);
//        boolean val2 =  DPLL(clauses2);
//        System.out.println("VAL2: "+val2);

//        if(val != val2)
//            System.out.println("DIFF");
        return DPLL(clauses);
    }


}
