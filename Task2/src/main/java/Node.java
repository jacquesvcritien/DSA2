public class Node
{
    int frequency;
    char character;

    Node right;
    Node left;

    public Node(int frequency, char character)
    {
        this.frequency = frequency;
        this.character = character;
    }

    public Node(int frequency, char character, Node right, Node left)
    {
        this.frequency = frequency;
        this.character = character;
        this.right = right;
        this.left = left;
    }
}


