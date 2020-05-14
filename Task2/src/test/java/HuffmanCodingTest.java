import exceptions.CharacterNotSupportedException;
import exceptions.NoFilePassedException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class HuffmanCodingTest
{
    //just for coverage
    HuffmanCoding huffmanCoding;
    FileOperation fileOperation;
    @Before
    public void setup(){
        huffmanCoding = new HuffmanCoding();
        fileOperation = new FileOperation();
        HuffmanCoding.reset();
    }

    @After
    public void teardown()
    {
        huffmanCoding = null;
        fileOperation = null;
    }

    /**
     * Integration test for huffmancoding
     */
    @Test
    public void test1() throws IOException, CharacterNotSupportedException {
        FileOperation.populateInitFrequencies("tests/test1.txt");
        HuffmanCoding.buildTree();
        HuffmanCoding.generateCodes(HuffmanCoding.getRoot(), "");
        HuffmanCoding.printCodes();
        char shortest = HuffmanCoding.getShortest();

        Assert.assertEquals("Asserting equals shortest code character", 'f', shortest);
        Assert.assertEquals("Asserting equals number of different codes", 6, HuffmanCoding.getCodes().size());

    }

    /**
     * Integration test for huffmancoding
     */
    @Test
    public void test2() throws IOException, CharacterNotSupportedException {
        FileOperation.populateInitFrequencies("tests/test2.txt");
        HuffmanCoding.buildTree();
        HuffmanCoding.generateCodes(HuffmanCoding.getRoot(), "");
        HuffmanCoding.printCodes();
        char shortest = HuffmanCoding.getShortest();

        Assert.assertEquals("Asserting equals shortest code character", 'D', shortest);
        Assert.assertEquals("Asserting equals number of different codes", 13, HuffmanCoding.getCodes().size());


    }

    /**
     * Integration test for huffmancoding given wrong input
     */
    @Test(expected = CharacterNotSupportedException.class)
    public void testWrongInput1() throws IOException, CharacterNotSupportedException {
        FileOperation.populateInitFrequencies("tests/test3.txt");

    }

    /**
     * Integration test for huffmancoding given wrong input
     */
    @Test(expected = CharacterNotSupportedException.class)
    public void testWrongInput2() throws IOException, CharacterNotSupportedException {
        FileOperation.populateInitFrequencies("tests/test4.txt");

    }

    /**
     * Integration test for huffmancoding given wrong input
     */
    @Test(expected = CharacterNotSupportedException.class)
    public void testWrongInput3() throws IOException, CharacterNotSupportedException {
        FileOperation.populateInitFrequencies("tests/test5.txt");

    }

    /**
     * Integration test for huffmancoding
     */
    @Test
    public void test3() throws IOException, CharacterNotSupportedException {
        FileOperation.populateInitFrequencies("tests/test6.txt");
        HuffmanCoding.buildTree();
        HuffmanCoding.generateCodes(HuffmanCoding.getRoot(), "");
        HuffmanCoding.printCodes();
        char shortest = HuffmanCoding.getShortest();

        Assert.assertEquals("Asserting equals shortest code character", 'Z', shortest);
        Assert.assertEquals("Asserting equals number of different codes", 33, HuffmanCoding.getCodes().size());


    }

    /**
     * Integration test for huffmancoding
     */
    @Test
    public void test4() throws IOException, CharacterNotSupportedException {
        FileOperation.populateInitFrequencies("tests/test7.txt");
        HuffmanCoding.buildTree();
        HuffmanCoding.generateCodes(HuffmanCoding.getRoot(), "");
        HuffmanCoding.printCodes();
        char shortest = HuffmanCoding.getShortest();

        Assert.assertEquals("Asserting equals shortest code character", 'b', shortest);


    }

    /**
     * Integration test for huffmancoding executor given no filename
     */
    @Test(expected = NoFilePassedException.class)
    public void testExecutorNoFilename() throws IOException, NoFilePassedException, CharacterNotSupportedException {
        String[] args = new String[0];
        HuffmanCodingExecutor.main(args);

    }

    /**
     * Integration test for huffmancoding
     */
    @Test
    public void test5() throws IOException, NoFilePassedException, CharacterNotSupportedException {
        FileOperation.populateInitFrequencies("tests/test8.txt");
        HuffmanCoding.buildTree();
        HuffmanCoding.generateCodes(HuffmanCoding.getRoot(), "");
        HuffmanCoding.printCodes();
        char shortest = HuffmanCoding.getShortest();

        Assert.assertEquals("Asserting equals shortest code character", 'A', shortest);
        Assert.assertEquals("Asserting equals number of different codes", 15, HuffmanCoding.getCodes().size());

    }

    /**
     * Integration test for huffmancoding
     */
    @Test
    public void test6() throws IOException, NoFilePassedException, CharacterNotSupportedException {
        FileOperation.populateInitFrequencies("tests/test9.txt");
        HuffmanCoding.buildTree();
        HuffmanCoding.generateCodes(HuffmanCoding.getRoot(), "");
        HuffmanCoding.printCodes();
        char shortest = HuffmanCoding.getShortest();

        Assert.assertEquals("Asserting equals shortest code character", 'a', shortest);

    }

    /**
     * tets for when a bad fiel path is passed as argument
     */
    @Test(expected = FileNotFoundException.class)
    public void testBadFilePath() throws IOException, NoFilePassedException, CharacterNotSupportedException {
        String[] args = new String[1];
        args[0] = "badfile.txt";
        HuffmanCodingExecutor.main(args);

    }



}