import java.util.ArrayList;

/**
 * Class for clause
 */
public class Clause {
    //arraylist of literals
    ArrayList<Literal> literals = new ArrayList<Literal>();

    /**
     * Constructor for clause
     * @param literals literals to set
     */
    public Clause(ArrayList<Literal> literals) {
        this.literals = literals;
    }

    /**
     * Empty constructor for clause
     */
    public Clause() {
        this.literals = new ArrayList<Literal>();
    }

    /**
     * Gette for literals
     * @return list of literals
     */
    public ArrayList<Literal> getLiterals() {
        return literals;
    }

    /**
     * Method to add literal
     * @param literal literal to add
     */
    public void addLiteral(Literal literal)
    {
        this.literals.add(literal);
    }

}
