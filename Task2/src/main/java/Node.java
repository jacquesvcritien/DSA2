import java.util.Comparator;

/**
 * Comparator to compare nodes by their frequency
 */
class NodeComparator implements Comparator<Node> {
    public int compare(Node x, Node y)
    {
        return x.getFrequency() - y.getFrequency();
    }
}

public class Node
{
    //member variable to hold frequency, i.e. number of times it appears
    private int frequency;
    //member variable to hold the character
    private char character;

    //right node
    Node right;
    //left node
    Node left;

    /**
     * Constructor
     * @param frequency frequency to set
     * @param character character to set
     */
    public Node(int frequency, char character)
    {
        this.frequency = frequency;
        this.character = character;
    }

    /**
     * Constructor
     * @param frequency frequency to set
     * @param character character to set
     * @param right right to set
     * @param left left to set
     */
    public Node(int frequency, char character, Node right, Node left)
    {
        this.frequency = frequency;
        this.character = character;
        this.right = right;
        this.left = left;
    }

    /**
     * Getter for character
     * @return character
     */
    public char getCharacter() {
        return character;
    }

    /**
     * Getter for frequency
     * @return frequency
     */
    public int getFrequency() {
        return frequency;
    }
}


