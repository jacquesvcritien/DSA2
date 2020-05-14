import exceptions.SyntaxErrorException;

/**
 * Class for literal
 */
public class Literal {
    //flag which holds if this class is negated
    private boolean negated;
    //symbol of literal
    private Symbol symbol;

    //empty constructor
    public Literal() { }

    /**
     * Constrctor for literal
     * @param stringLiteral string from which to get literal
     * @throws SyntaxErrorException
     */
    public Literal(String stringLiteral) throws SyntaxErrorException {
        //check literal
        try {
            //if length is more than 2 or is 2 and there is no ! at the beginning, throw an errpr
            if (stringLiteral.length() > 2 || (stringLiteral.length() == 2 && !stringLiteral.startsWith("!")))
                throw new SyntaxErrorException("Literal is not in the required format");

            //negate dif length is 2
            this.negated = stringLiteral.length() == 2;

            //if negated, substring and get the value of the symbol, otherwise get the value directly
            this.symbol = (this.negated) ? Symbol.valueOf(stringLiteral.substring(stringLiteral.length()-1)) : Symbol.valueOf(stringLiteral);

        }
        catch(IllegalArgumentException e)
        {
            throw new SyntaxErrorException("Only letters w, x, y and z can be used");
        }

    }

    /**
     * Simple constructor
     * @param symbol symbol to add
     * @param negated if negated
     */
    public Literal(Symbol symbol, boolean negated) {
        this.symbol = symbol;
        this.negated = negated;
    }

    /**
     * Getter for negated
     * @return true if negated
     */
    public boolean isNegated() {
        return negated;
    }

    /**
     * Getter for symbol
     * @return symbol
     */
    public Symbol getSymbol() {
        return symbol;
    }


    /**
     * The setter for negated
     * @param negated negated to set
     */
    public void setNegated(boolean negated) {
        this.negated = negated;
    }

    /**
     * Setter for symbol
     * @param symbol symbol to set
     */
    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

        /**
     * Method to compare a literal
     * @param obj literal to compare against
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Literal))
            return false;

        Literal literal = (Literal) obj;
        return ((this.negated == literal.negated) && (this.symbol == literal.symbol));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + symbol.hashCode();
        result = 31 * result + ((Boolean)negated).hashCode();
        return result;
    }

    /**
     * Method to clone a literal
     * @return a cloned literal
     */
    public Literal clone()
    {
        Literal toReturn = new Literal();
        toReturn.negated = this.negated;
        toReturn.symbol = this.symbol;
        return toReturn;
    }
}
