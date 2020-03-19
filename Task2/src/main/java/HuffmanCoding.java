import java.util.*;

public class HuffmanCoding
{
    //hashmap to hold frequencies
    static HashMap<Character, Integer> frequencies = new HashMap<Character, Integer>();
    //priority queue to hold nodes to build trees
    static PriorityQueue<Node> sortedChars;
    //treemap to hold codes sorted by character
    static TreeMap<Character, String> codes = new TreeMap<Character, String>();
    //root
    static Node root = null;

    /**
     * Method to initialise frequencies
     */
    public static void initialiseFrequencies()
    {
        char ch;
        for (ch = 'A'; ch <= 'Z'; ++ch)
            frequencies.put(Character.valueOf(ch), 0);

        for (ch = 'a'; ch <= 'z'; ++ch)
            frequencies.put(Character.valueOf(ch), 0);

        for (ch = '0'; ch <= '9'; ++ch)
            frequencies.put(Character.valueOf(ch), 0);

        //initialise priority queue
        sortedChars = new PriorityQueue<Node>(frequencies.size(), new NodeComparator());
    }

    /**
     * Method to build the tree
     */
    public static void buildTree()
    {
        //loop until only 1 node is left in the priority queue
        while(sortedChars.size() >= 2)
        {
            //get 2 smallest nodes
            Node smallest = sortedChars.poll();
            Node secondSmallest = sortedChars.poll();

            //add their frequency
            Integer newFreq = smallest.frequency + secondSmallest.frequency;
            //create a new node
            Node newNode = new Node(newFreq, '#', secondSmallest, smallest);

            //add node to queue
            sortedChars.add(newNode);
            //set the new node as the root
            root = newNode;
        }
    }

    /**
     * Method to loop through codes and print them
     */
    public static void printCodes()
    {
        for (Map.Entry<Character, String> entry : codes.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }
    }

    /**
     * Method to generate codes
     * @param topNode node to execute
     * @param code current code up till now
     */
    public static void generateCodes(Node topNode, String code)
    {
        //get topNode's character
        Character nodeChar = topNode.character;
        //base case
        if (nodeChar != '#' && topNode.left == null && topNode.right == null)
        {
            //add to code and return
            codes.put(nodeChar, code);
            return;
        }

        //recall generateCodes recursively for the right and left nodes and add 1 and 0 to the code respectively
        generateCodes(topNode.right, code + "1");
        generateCodes(topNode.left, code + "0");
    }

}
