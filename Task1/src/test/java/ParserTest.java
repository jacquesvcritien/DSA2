import exceptions.SyntaxErrorException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
public class ParserTest {

    //just for coverage
    Parser parser = new Parser();
    Clause clause = new Clause(new ArrayList<Literal>());

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
        Set<Clause> clauses = Parser.parseString("(x,y)(w,y)");

        ArrayList<Clause> clausesList = new ArrayList<Clause>(clauses);

        assertEquals("Asserting there are 2 clauses", 2, clauses.size());
        assertEquals("Asserting clause 1 has 2 literals", 2, clausesList.get(0).getLiterals().size());
        assertEquals("Asserting clause 2 has 2 literals", 2, clausesList.get(1).getLiterals().size());
    }

    @Test
    public void testParseStringCaseDoesntMatter() throws SyntaxErrorException {
        Set<Clause> clauses = Parser.parseString("(x,Y)(W,y)");
        ArrayList<Clause> clausesList = new ArrayList<Clause>(clauses);

        assertEquals("Asserting there are 2 clauses", 2, clauses.size());
        assertEquals("Asserting clause 1 has 2 literals", 2, clausesList.get(0).getLiterals().size());
        assertEquals("Asserting clause 2 has 2 literals", 2, clausesList.get(1).getLiterals().size());
    }


}