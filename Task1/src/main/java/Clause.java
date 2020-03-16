import java.util.ArrayList;

public class Clause {
    ArrayList<Literal> literals = new ArrayList<Literal>();

    //constructor for clause
    public Clause(ArrayList<Literal> literals) {
        this.literals = literals;
    }

    //constructor for clause
    public Clause() {
        this.literals = new ArrayList<Literal>();
    }

    //getter for literals
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
