import exceptions.SyntaxErrorException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class LiteralTest {

    Literal literal;
    @Before
    public void setup() {
        literal = new Literal(Symbol.y, false);
    }

    @After
    public void teardown() {
        literal = null;
    }

    /**
     * Test for having length bigger than 2
     * @throws SyntaxErrorException
     */
    @Test(expected = SyntaxErrorException.class)
    public void testLiteralConstructor() throws SyntaxErrorException {
        literal = new Literal("(xzy)");
    }

    /**
     * Test for having length 2 but no starting !
     * @throws SyntaxErrorException
     */
    @Test(expected = SyntaxErrorException.class)
    public void testLiteralConstructor2() throws SyntaxErrorException {
        literal = new Literal("xz");
    }

    /**
     * Test for equals
     */
    @Test
    public void testEqualsWhenEqual() {
        Literal newLiteral = new Literal(Symbol.y, false);
        Assert.assertTrue("Asserting literals equal", literal.equals(newLiteral));
    }

    /**
     * Test for equals not equal
     */
    @Test
    public void testEqualsWhenNotEqual() {
        Literal newLiteral = new Literal(Symbol.x, false);
        Assert.assertFalse("Asserting literals equal", literal.equals(newLiteral));
    }

    /**
     * Test for equals not equal
     */
    @Test
    public void testEqualsWhenNotEqual2() {
        Literal newLiteral = new Literal(Symbol.y, true);
        Assert.assertFalse("Asserting literals equal", literal.equals(newLiteral));
    }

    /**
     * Test for equals not equal
     */
    @Test
    public void testEqualsWhenNotEqualBoth() {
        Literal newLiteral = new Literal(Symbol.x, true);
        Assert.assertFalse("Asserting literals equal", literal.equals(newLiteral));
    }

    /**
     * Test for equals when null
     */
    @Test
    public void testEqualsNull() {
        Assert.assertFalse("Asserting literals not equal", literal.equals(null));
    }

    /**
     * Test for equals when same object
     */
    @Test
    public void testEqualsSameObject() {
        Assert.assertTrue("Asserting literals equal", literal.equals(literal));
    }

}