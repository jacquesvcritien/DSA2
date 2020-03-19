import java.util.Comparator;

/**
 * Comparator to compare nodes by their frequency
 */
class NodeComparator implements Comparator<Node> {
    public int compare(Node x, Node y)
    {
        return x.frequency - y.frequency;
    }
}

