import java.util.ArrayList;

public class Clause {
    ArrayList<Literal> literals = new ArrayList<Literal>();

    //constructor for clause
    public Clause(ArrayList<Literal> literals) {
        this.literals = literals;
    }

    //getter for literals
    public ArrayList<Literal> getLiterals() {
        return literals;
    }

    //setter for literals
    public void setLiterals(ArrayList<Literal> literals) {
        this.literals = literals;
    }
}
