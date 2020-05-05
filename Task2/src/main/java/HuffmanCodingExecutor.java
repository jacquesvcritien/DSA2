import exceptions.CharacterNotSupportedException;
import exceptions.NoFilePassedException;

import java.io.IOException;

public class HuffmanCodingExecutor {
    public static void main(String args[]) throws IOException, CharacterNotSupportedException, NoFilePassedException {
        if(args.length == 0|| args[0] == "")
            throw new NoFilePassedException("A filename should be passed");

//        HuffmanCoding.initialiseFrequencies();
        FileOperation.populateInitFrequencies(args[0]);
        HuffmanCoding.buildTree();
        HuffmanCoding.generateCodes(HuffmanCoding.getRoot(), "");
        HuffmanCoding.printCodes();

    }
}
