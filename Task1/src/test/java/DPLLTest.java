import exceptions.SyntaxErrorException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class DPLLTest {

    //just for coverage
    DPLL dpll = new DPLL();

    @Test
    public void checkTriviallyUnSatEmpty() {
        Set<Clause> emptyClauses = new HashSet<Clause>();
        assertFalse(DPLL.checkTriviallyUnSat(emptyClauses));
    }

    @Test
    public void checkTriviallyUnSatOneClause() throws SyntaxErrorException {
        Set<Clause> clauses = Parser.parseString("(x)");
        assertFalse(DPLL.checkTriviallyUnSat(clauses));
    }

    @Test
    public void checkTriviallyUnSatTwoClauseFalse() throws SyntaxErrorException {
        Set<Clause> clauses = Parser.parseString("(x,y)(x,z)(!x,y)");
        assertFalse(DPLL.checkTriviallyUnSat(clauses));
    }

    @Test
    public void checkTriviallyUnSatTwoClauseFalse2() throws SyntaxErrorException {
        Set<Clause> clauses = Parser.parseString("(x)(y)");
        assertFalse(DPLL.checkTriviallyUnSat(clauses));
    }

    @Test
    public void checkTriviallyUnSatTrue() throws SyntaxErrorException {
        Set<Clause> clauses = Parser.parseString("(x)(x,z)(!x,y)(!x)");
        assertTrue(DPLL.checkTriviallyUnSat(clauses));
    }

    @Test
    public void testRemoveTriviallySatEmpty() throws SyntaxErrorException {
        Set<Clause> emptyClauses = new HashSet<Clause>();
        assertEquals(emptyClauses, DPLL.removeTriviallySat(emptyClauses));
    }

    @Test
    public void testRemoveTriviallySatNotSatBySize() throws SyntaxErrorException {
        Set<Clause> clauses = Parser.parseString("(x,y)(!x,z)");
        assertEquals(clauses, DPLL.removeTriviallySat(clauses));
    }

    @Test
    public void testRemoveTriviallySatBySizeRemove1() throws SyntaxErrorException {
        Set<Clause> clauses = Parser.parseString("(x,!x)(y)");
        assertEquals(clauses.size() - 1, DPLL.removeTriviallySat(clauses).size());
    }

    @Test
    public void testRemoveTriviallySatBySizeRemove2() throws SyntaxErrorException {
        Set<Clause> clauses = Parser.parseString("(x,!x)(y)(!z,z)");
        assertEquals(clauses.size() - 2, DPLL.removeTriviallySat(clauses).size());
    }

    @Test
    public void testCheckEmptyClausesEmpty() throws SyntaxErrorException {

        Set<Clause> clauses = Parser.parseString("(x,!x)(y)(!z,z)");
        Clause clause = new Clause();
        clause.literals = new ArrayList<Literal>();

        clauses.add(clause);

        assertTrue(DPLL.checkEmptyClause(clauses));
    }

    @Test
    public void testCheckEmptyClausesNonEmpty() throws SyntaxErrorException {
        Set<Clause> clauses = Parser.parseString("(x,!x)(y)(!z,z)");

        assertFalse(DPLL.checkEmptyClause(clauses));
    }


    @Test
    public void testExhaustivelyApply1LiteralRule() throws SyntaxErrorException {
        Set<Clause> clauses = Parser.parseString("(x)(!x)");
        Clause cl = new Clause();
        Literal lt = new Literal("x");
        cl.addLiteral(lt);
        clauses = DPLL.exhaustivelyApply1LiteralRule(cl, clauses);

        assertEquals(1, clauses.size());
    }

    @Test
    public void testExhaustivelyApply1LiteralRule2() throws SyntaxErrorException {
        Set<Clause> clauses = Parser.parseString("(!x)(x)");
        Clause cl = new Clause();
        Literal lt = new Literal("x");
        cl.addLiteral(lt);
        clauses = DPLL.exhaustivelyApply1LiteralRule(cl, clauses);

        assertEquals(1, clauses.size());
    }

    @Test
    public void testGetPureLiteral() throws SyntaxErrorException {
        Set<Clause> clauses = Parser.parseString("(!x)(y, !x)(!x, w)");

        Literal lt = DPLL.getPureLiteral(clauses);

        if(lt.getSymbol() == Symbol.x)
            assertTrue(lt.isNegated());
        else
        {
            assertFalse(lt.isNegated());
            assertTrue("Asserting symbol", Symbol.y.equals(lt.getSymbol()) || Symbol.w.equals(lt.getSymbol()));
        }

    }

    @Test
    public void testApplyPureLiteralRule() throws SyntaxErrorException {
        Set<Clause> clauses = Parser.parseString("(!x)(y, x)(z,w)(y, w)");
        Parser.printParsed(clauses);


        Literal pureLiteral = DPLL.getPureLiteral(clauses);

        while(pureLiteral != null)
        {
            clauses = DPLL.applyPureLiteralRule(pureLiteral, clauses);
            pureLiteral = DPLL.getPureLiteral(clauses);
        }

        assertEquals(0, clauses.size());
    }

    @Test
    public void testChooseLiteral() throws SyntaxErrorException {
        Set<Clause> clauses = Parser.parseString("(!x)(y, x)(!y,z)(y, w)");

        Literal literalChosen = DPLL.chooseLiteral(clauses);

        assertEquals(Symbol.y, literalChosen.getSymbol());
    }


    @Test
    public void testDPLL() throws SyntaxErrorException {
        Set<Clause> clauses = Parser.parseString("(x, !y, z)(v, w, y)(v, !x, !z)");
        boolean value = DPLL.DPLL(clauses);

        assertTrue(value);
    }

    @Test
    public void testDPLL2() throws SyntaxErrorException {
        Set<Clause> clauses = Parser.parseString("(x, y, z)(x, !y)(y, !z)(z, !x)(!x, !y, !z)");

        assertFalse(DPLL.DPLL(clauses));
    }

    @Test
    public void testDPLL3() throws SyntaxErrorException {
        Set<Clause> clauses = Parser.parseString("(x)(!x)");

        assertFalse(DPLL.DPLL(clauses));
    }

    @Test
    public void testDPLL4() throws SyntaxErrorException {
        Set<Clause> clauses = Parser.parseString("(!x)(!x,y)(!x,z)");

        assertTrue(DPLL.DPLL(clauses));
    }

    @Test
    public void testDPLL5() throws SyntaxErrorException {
        Set<Clause> clauses = Parser.parseString("(!x,y)(!x,z)");

        assertTrue(DPLL.DPLL(clauses));
    }

    @Test
    public void testDPLL6() throws SyntaxErrorException {
        Set<Clause> clauses = Parser.parseString("(w)(x, !y)(z,x)(y,!x)(x, !x)(z, !y)");

        assertTrue(DPLL.DPLL(clauses));
    }

    @Test
    public void testDPLL7() throws SyntaxErrorException {
        Set<Clause> clauses = Parser.parseString("(x,y)(w)(!x, z)");

        assertTrue(DPLL.DPLL(clauses));
    }

    @Test
    public void testDPLL8() throws SyntaxErrorException {
        Set<Clause> clauses = Parser.parseString("(x,y)(x,z)(!y, !z)");

        assertTrue(DPLL.DPLL(clauses));
    }

    @Test
    public void testDPLL9() throws SyntaxErrorException {
        Set<Clause> clauses = Parser.parseString("(x)(!x, y)");

        assertTrue(DPLL.DPLL(clauses));
    }

    @Test
    public void testDPLL10() throws SyntaxErrorException {
        Set<Clause> clauses = Parser.parseString("(!x)(!x,y)");

        assertTrue(DPLL.DPLL(clauses));
    }

}