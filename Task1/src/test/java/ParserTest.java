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

    @Test
    public void checkTriviallyUnSatEmpty() {
        ArrayList<Clause> emptyClauses = new ArrayList<Clause>();
        assertFalse(Parser.checkTriviallyUnSat(emptyClauses));
    }

    @Test
    public void checkTriviallyUnSatOneClause() throws SyntaxErrorException {
        ArrayList<Clause> clauses = Parser.parseString("(x)");
        assertFalse(Parser.checkTriviallyUnSat(clauses));
    }

    @Test
    public void checkTriviallyUnSatTwoClauseFalse() throws SyntaxErrorException {
        ArrayList<Clause> clauses = Parser.parseString("(x,y)(x,z)(!x,y)");
        assertFalse(Parser.checkTriviallyUnSat(clauses));
    }

    @Test
    public void checkTriviallyUnSatTrue() throws SyntaxErrorException {
        ArrayList<Clause> clauses = Parser.parseString("(x)(x,z)(!x,y)(!x)");
        assertTrue(Parser.checkTriviallyUnSat(clauses));
    }

    @Test
    public void testRemoveTriviallySatEmpty() throws SyntaxErrorException {
        ArrayList<Clause> emptyClauses = new ArrayList<Clause>();
        assertEquals(emptyClauses, Parser.removeTriviallySat(emptyClauses));
    }

    @Test
    public void testRemoveTriviallySatNotSatBySize() throws SyntaxErrorException {
        ArrayList<Clause> clauses = Parser.parseString("(x,y)(!x,z)");
        assertEquals(clauses, Parser.removeTriviallySat(clauses));
    }

    @Test
    public void testRemoveTriviallySatBySizeRemove1() throws SyntaxErrorException {
        ArrayList<Clause> clauses = Parser.parseString("(x,!x)(y)");
        assertEquals(clauses.size() - 1, Parser.removeTriviallySat(clauses).size());
    }

    @Test
    public void testRemoveTriviallySatBySizeRemove2() throws SyntaxErrorException {
        ArrayList<Clause> clauses = Parser.parseString("(x,!x)(y)(!z,z)");
        assertEquals(clauses.size() - 2, Parser.removeTriviallySat(clauses).size());
    }

    @Test
    public void testCheckEmptyClausesEmpty() throws SyntaxErrorException {

        ArrayList<Clause> clauses = Parser.parseString("(x,!x)(y)(!z,z)");
        Clause clause = new Clause();
        clause.literals = new ArrayList<Literal>();

        clauses.add(clause);

        assertTrue(Parser.checkEmptyClause(clauses));
    }

    @Test
    public void testCheckEmptyClausesNonEmpty() throws SyntaxErrorException {
        ArrayList<Clause> clauses = Parser.parseString("(x,!x)(y)(!z,z)");

        assertFalse(Parser.checkEmptyClause(clauses));
    }


    @Test
    public void testDPLL() throws SyntaxErrorException {
        ArrayList<Clause> clauses = Parser.parseString("(x,!x)(y)(!z,z)");

        assertFalse(Parser.DPLL(clauses));
    }

    @Test
    public void testDPLL2() throws SyntaxErrorException {
        ArrayList<Clause> clauses = Parser.parseString("(x,y)(x,z)(!x,y)");

        assertFalse(Parser.DPLL(clauses));
    }

    @Test
    public void testDPLL3() throws SyntaxErrorException {
        ArrayList<Clause> clauses = Parser.parseString("(x)(!x)");

        assertFalse(Parser.DPLL(clauses));
    }

    @Test
    public void testDPLL4() throws SyntaxErrorException {
        ArrayList<Clause> clauses = Parser.parseString("(z)");

        assertFalse(Parser.DPLL(clauses));
    }

    @Test
    public void testDPLL5() throws SyntaxErrorException {
        ArrayList<Clause> clauses = Parser.parseString("(x, !x)");

        assertTrue(Parser.DPLL(clauses));
    }

    @Test
    public void testExhaustivelyApply1LiteralRule() throws SyntaxErrorException {
        ArrayList<Clause> clauses = Parser.parseString("(x)(!x)");
        Clause cl = new Clause();
        Literal lt = new Literal("x");
        cl.addLiteral(lt);
        clauses = Parser.exhaustivelyApply1LiteralRule(cl, clauses);

        assertEquals(1, clauses.size());
    }

    @Test
    public void testDPLL6() throws SyntaxErrorException {
        ArrayList<Clause> clauses = Parser.parseString("(x)(y, !x)(x, w)");

        assertTrue(Parser.DPLL(clauses));
    }

    @Test
    public void testGetPureLiteral() throws SyntaxErrorException {
        ArrayList<Clause> clauses = Parser.parseString("(!x)(y, !x)(!x, w)");

        Literal lt = Parser.getPureLiteral(clauses);

        assertEquals(true, lt.negated);
        assertEquals(Symbol.x, lt.symbol);
    }

    @Test
    public void testApplyPureLiteralRule() throws SyntaxErrorException {
        ArrayList<Clause> clauses = Parser.parseString("(!x)(y, x)(z,w)(y, w)");

        Literal pureLiteral = Parser.getPureLiteral(clauses);

        while(pureLiteral != null)
        {
            clauses = Parser.applyPureLiteralRule(pureLiteral, clauses);
            Parser.printParsed(clauses);
            pureLiteral = Parser.getPureLiteral(clauses);
        }

        assertEquals(0, clauses.size());
    }

}