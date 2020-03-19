import exceptions.CharacterNotSupportedException;
import exceptions.NoFilePassedException;

import java.io.IOException;
import java.util.HashMap;

public class HuffManCodingExecutor {
    public static void main(String args[]) throws IOException, CharacterNotSupportedException, NoFilePassedException
    {
//        if(args[0] == "")
//            throw new NoFilePassedException("A filename should be passed");

//        HuffmanCoding.initialiseFrequencies();
        FileOperation.populateInitFrequencies("text.txt");
        HuffmanCoding.buildTree();
        HuffmanCoding.generateCodes(HuffmanCoding.root, "");
        HuffmanCoding.printCodes();

    }
}
