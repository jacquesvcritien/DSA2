import exceptions.SyntaxErrorException;

public class Literal {
    boolean negated;
    Symbol symbol;

    //constructor for literal
    public Literal(String stringLiteral) throws SyntaxErrorException {
        //check literal

        try {
            if (stringLiteral.length() > 3 || (stringLiteral.length() == 2 && !stringLiteral.startsWith("!")))
                throw new SyntaxErrorException("Literal is not in the required format");

            this.negated = stringLiteral.length() == 2;

            this.symbol = (this.negated) ? Symbol.valueOf(stringLiteral.substring(stringLiteral.length()-1).toLowerCase()) : Symbol.valueOf(stringLiteral.toLowerCase());

        }
        catch(IllegalArgumentException e)
        {
            throw new SyntaxErrorException("Only letters w, x, y and z can be used");
        }

    }

    //getter for negated
    public boolean isNegated() {
        return negated;
    }

    //getter for symbol
    public Symbol getSymbol() {
        return symbol;
    }
}
