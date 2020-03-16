import exceptions.SyntaxErrorException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
public class ParserTest {

    @Test(expected = SyntaxErrorException.class)
    public void testParseStringEmpty() throws SyntaxErrorException {
        Parser.parseString("");
    }

    @Test(expected = SyntaxErrorException.class)
    public void testParseStringMissingFirstParentheses() throws SyntaxErrorException {
        Parser.parseString("x,y)(x,y)");
    }

    @Test(expected = SyntaxErrorException.class)
    public void testParseStringMissingLastParentheses() throws SyntaxErrorException {
        Parser.parseString("(x,y)(x,y");
    }

    @Test(expected = SyntaxErrorException.class)
    public void testParseStringWrongLetters() throws SyntaxErrorException {
        Parser.parseString("(a,y)(c,y)");
    }

    @Test
    public void testParseStringBySize() throws SyntaxErrorException {
        ArrayList<Clause> clauses = Parser.parseString("(x,y)(!w,y)");

        assertEquals("Asserting there are 2 clauses", 2, clauses.size());
        assertEquals("Asserting clause 1 has 2 literals", 2, clauses.get(0).getLiterals().size());
        assertEquals("Asserting clause 2 has 2 literals", 2, clauses.get(1).getLiterals().size());
        assertTrue("Asserting the first literal of clause 2 is negated", clauses.get(1).getLiterals().get(0).isNegated());
    }

    @Test
    public void testParseStringCaseDoesntMatter() throws SyntaxErrorException {
        ArrayList<Clause> clauses = Parser.parseString("(x,Y)(W,y)");

        assertEquals("Asserting there are 2 clauses", 2, clauses.size());
        assertEquals("Asserting clause 1 has 2 literals", 2, clauses.get(0).getLiterals().size());
        assertEquals("Asserting clause 2 has 2 literals", 2, clauses.get(1).getLiterals().size());
    }





}
