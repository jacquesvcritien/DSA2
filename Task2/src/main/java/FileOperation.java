import exceptions.CharacterNotSupportedException;

import java.io.*;
import java.util.Map;
import java.util.PriorityQueue;

public class FileOperation {

//    /**
//     * Method to populate frequencies if all characters have to listed for each file and they were already initialised
//     * @param filename filename
//     * @throws IOException if a file is not found
//     * @throws CharacterNotSupportedException if a wrong character is included in the file
//     */
//    public static void populateFrequencies(String filename) throws IOException, CharacterNotSupportedException {
//        //find file
//        File file = new File(filename);
//        FileReader fileReader = new FileReader(file);
//        BufferedReader bufferedReader = new BufferedReader(fileReader);
//
//        //to keep the currentFrequency
//        int currentVal;
//
//        //get ascii integer
//        int asciiCode = bufferedReader.read();
//        //while is found in ascii table
//        while(asciiCode != -1)
//        {
//            //get character with that ascii code
//            char character = (char) asciiCode;
//            //if not in accepted character range, throw error
//            if((character < 'A' || character > 'Z') && (character < 'a' || character > 'z') && (character < '0' || character > '9'))
//                throw new CharacterNotSupportedException("File should only includes A-Z, a-z or 0-9 characters");
//
//            //get current frequency
//            currentVal = HuffmanCoding.getFrequencies().get(character);
//            //add 1 to it
//            HuffmanCoding.getFrequencies().put(character, currentVal+1);
//            //read next char
//            asciiCode = bufferedReader.read();
//        }
//
//        //to hold node to add
//        Node newNode;
//
//        //loop through frequencies, create a node for each character and add it to the queue
//        for (Map.Entry<Character, Integer> entry : HuffmanCoding.getFrequencies().entrySet()) {
//            newNode = new Node(entry.getValue(), entry.getKey());
//            HuffmanCoding.getSortedChars().add(newNode);
//        }
//    }

    /**
     * Method to populate frequencies
     * @param filename filename
     * @throws IOException if a file is not found
     * @throws CharacterNotSupportedException if a wrong character is included in the file
     */
    public static void populateInitFrequencies(String filename) throws IOException, CharacterNotSupportedException {
        //find file
        File file = new File(filename);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        //to keep the current frequency
        int currentVal;

        //get ascii integer
        int asciiCode = bufferedReader.read();
        //while is found in ascii table
        while(asciiCode != -1)
        {
            //get character with that ascii code
            char character = (char) asciiCode;
            //if not in accepted character range, throw error
            if((character < 'A' || character > 'Z') && (character < 'a' || character > 'z') && (character < '0' || character > '9'))
                throw new CharacterNotSupportedException("File should only includes A-Z, a-z or 0-9 characters");

            //if frequency exist in hashmap, add to it
            if(HuffmanCoding.getFrequencies().containsKey(character)) {
                currentVal = HuffmanCoding.getFrequencies().get(character);
                HuffmanCoding.getFrequencies().put(character, currentVal + 1);
            }
            //if it does not exist, initialise frequency for letter
            else
            {
                HuffmanCoding.getFrequencies().put(character, 1);
            }

            //read next character
            asciiCode = bufferedReader.read();
        }

        //to hold node to add
        Node newNode;

        //initialise priority queue for nodes
        HuffmanCoding.setSortedChars(new PriorityQueue<Node>(HuffmanCoding.getFrequencies().size(), new NodeComparator()));

        //loop through frequencies, create a node for each character and add it to the queue
        for (Map.Entry<Character, Integer> entry : HuffmanCoding.getFrequencies().entrySet()) {
            newNode = new Node(entry.getValue(), entry.getKey());
            HuffmanCoding.getSortedChars().add(newNode);
        }

    }
}
