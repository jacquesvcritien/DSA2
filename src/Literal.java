public class Literal {
    boolean negated;
    Symbol symbol;

    //constructor for literal
    public Literal(Symbol symbol, boolean negated)
    {
        this.negated = negated;
        this.symbol = symbol;
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
