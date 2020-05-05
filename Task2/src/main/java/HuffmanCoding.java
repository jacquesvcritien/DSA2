import exceptions.CharacterNotSupportedException;

import java.io.IOException;
import java.util.*;

public class HuffmanCoding
{
    //hashmap to hold frequencies
    private static HashMap<Character, Integer> frequencies = new HashMap<Character, Integer>();
    //priority queue to hold nodes to build trees
    private static PriorityQueue<Node> sortedChars;
    //treemap to hold codes sorted by character
    private static TreeMap<Character, String> codes = new TreeMap<Character, String>();
    //root
    private static Node root = null;

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
            Integer newFreq = smallest.getFrequency() + secondSmallest.getFrequency();
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
        Character nodeChar = topNode.getCharacter();
        //base case
        if (topNode.left == null)
        {
            //add to the treemap of codes, since it is a treemap, its already sorted on insert
            codes.put(nodeChar, code);
            return;
        }

        //recall generateCodes recursively for the right and left nodes and add 1 and 0 to the code respectively
        generateCodes(topNode.right, code + "1");
        generateCodes(topNode.left, code + "0");
    }


    /**
     * Method to print the shortest code
     * @return
     */
    public static Character getShortest()
    {
        //set shortest length to max int value
        int shortestLength = Integer.MAX_VALUE;
        //initialise holder for shortest character
        Character shortestChar = null;

        //go through every code
        for(Map.Entry<Character, String> entry : codes.entrySet())
        {
            //get its length
            int currentCodeLength = entry.getValue().length();
            // if the code's length is smaller than the current shortest
            if(currentCodeLength < shortestLength)
            {
                //set the shortest
                shortestChar = entry.getKey();
                shortestLength = currentCodeLength;
            }
        }

        System.out.println("SHORTEST: "+shortestChar);
        //return the character with the shortest code
        return shortestChar;
    }

//    /**
//     * Method to print the largest code
//     * @return
//     */
//    public static Character getLongest()
//    {
//        //holder for biggest code
//        int biggestCode = -1;
//        //holder for longest length
//        int longestLength = -1;
//        //holder for character with longest code
//        Character longestChar = null;
//
//        //go through every code
//        for(Map.Entry<Character, String> entry : codes.entrySet())
//        {
//            //get its code length
//            int currentCodeLength = entry.getValue().length();
//            //get its code value
//            int currentCode = Integer.parseInt(entry.getValue());
//
//            //if the length is bigger than the current
//            if(currentCodeLength>longestLength)
//            {
//                //set new longest
//                longestChar = entry.getKey();
//                longestLength = currentCodeLength;
//                biggestCode = currentCode;
//            }
//            //if it is equal
//            else if(currentCodeLength>longestLength)
//            {
//                //if it has a smaller value than the current longest
//                if(currentCode < biggestCode)
//                {
//                    //set new longest
//                    longestChar = entry.getKey();
//                    biggestCode = currentCode;
//                }
//
//            }
//        }
//
//        System.out.println(longestChar);
//        //return the character with the longest code
//        return longestChar;
//
//    }

    /**
     * Method to reset for testing
     */
    public static void reset()
    {
        frequencies = new HashMap<Character, Integer>();
        sortedChars = null;
        codes = new TreeMap<Character, String>();
        root = null;
    }

    /**
     * Getter for codes
     * @return treemap of codes
     */
    public static TreeMap<Character, String> getCodes() {
        return codes;
    }

    /**
     * Getter for frequencies
     * @return hashmap containing frequencies
     */
    public static HashMap<Character, Integer> getFrequencies() {
        return frequencies;
    }

    /**
     * Getter for sorted Chars
     * @return priority queue for characters nodes
     */
    public static PriorityQueue<Node> getSortedChars() {
        return sortedChars;
    }

    /**
     * Getter for root node
     * @return root node
     */
    public static Node getRoot() {
        return root;
    }

    /**
     * Setter for sortedChars
     * @param sortedChars sortedChars to set
     */
    public static void setSortedChars(PriorityQueue<Node> sortedChars) {
        HuffmanCoding.sortedChars = sortedChars;
    }
}
