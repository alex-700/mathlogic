package parser;

import expressions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserTests {

    @Test
    void testParse() throws Exception {
        Expression e = new And(new Variable("a"), new Variable("b"));
        assertEquals(e, ExpressionParser.parse("a&b"));
        assertEquals(e, ExpressionParser.parse("a& b"));
        assertEquals(e, ExpressionParser.parse("a &b"));
        assertEquals(e, ExpressionParser.parse("a & b"));
        assertEquals(e, ExpressionParser.parse("      a            &               b      "));
        e = new Or(new Variable("a"), new Variable("b"));
        assertEquals(e, ExpressionParser.parse("a|b"));
        assertEquals(e, ExpressionParser.parse("a| b"));
        assertEquals(e, ExpressionParser.parse("a |b"));
        assertEquals(e, ExpressionParser.parse("a | b"));
        assertEquals(e, ExpressionParser.parse("      a            |               b      "));
        e = new Entailment(new Variable("a"), new Variable("b"));
        assertEquals(e, ExpressionParser.parse("a->b"));
        assertEquals(e, ExpressionParser.parse("a-> b"));
        assertEquals(e, ExpressionParser.parse("a ->b"));
        assertEquals(e, ExpressionParser.parse("a -> b"));
        assertEquals(e, ExpressionParser.parse("      a            ->               b      "));
        e = new Not(new Variable("a"));
        assertEquals(e, ExpressionParser.parse("!a"));
        assertEquals(e, ExpressionParser.parse("! a"));
        assertEquals(e, ExpressionParser.parse("             ! a"));
    }

    @Test
    void testMiddleParse() throws Exception {
        Expression e = new Entailment(new Not(new Not(new Entailment(new Not(new Variable("a")), new Not(new Variable("b"))))), new Not(new Variable("a")));
        assertEquals(e, ExpressionParser.parse("!!(!a->!b)->!a"));

        e = new Or(new Not(new Variable("a")), new Not(new Entailment(new Not(new Variable("b")), new And(new Variable("b"), new Variable("a")))));
        assertEquals(e, ExpressionParser.parse("!a|!(!b->b&a)"));

        e = new Or(new Entailment(new Not(new Variable("q")), new Not(new Variable("s"))), new Or(new Variable("s"), new Not(new Variable("q"))));
        assertEquals(e, ExpressionParser.parse("(!q->!s)|(s|!q)"));

        e = new Not(new Or(new Entailment(new Variable("a"), new Not(new Variable("b"))), new Entailment(new Variable("b"), new Not(new Variable("a")))));
        assertEquals(e, ExpressionParser.parse("!((a->!b)|(b->!a))"));
    }

    @Test
    void testHardParse() throws Exception {
        Expression e = new Or(new Not(new Or(new Variable("a"), new Variable("b"))), new And(new And(new Variable("a"), new Not(new Not(new Variable("b")))),
                new Or(new Variable("c"), new And(new Variable("a"), new Entailment(new Variable("b"), new And(new Variable("a"), new Variable("c")))))));
        assertEquals(e, ExpressionParser.parse("!(a|b)|((a&!!b)&(c|a&(b->a&c)))"));

        e = new Not(new Not(new Or(new And(new Or(new Variable("a"), new Not(new Variable("b"))), new Or(new Variable("c"), new Variable("d"))),
                new Or(new And(new And(new Variable("a"), new Variable("c")), new Not(new Variable("d"))), new Not(new Variable("b"))))));
        assertEquals(e, ExpressionParser.parse("!!(((a|!b)&(c|d))|(((a&c)&!d)|!b))"));

        e = new Not(new Entailment(new Or(new Variable("a"), new Entailment(new Not(new Entailment(new Variable("b"), new Variable("a"))), new Or(new Variable("c"),
                new Not(new Variable("b"))))), new And(new Not(new Entailment(new Variable("a"), new Variable("c"))), new Not(new Variable("b")))));
        assertEquals(e, ExpressionParser.parse("!((a|(!(b->a)->(c|!b)))->(!(a->c)&!b))"));

        e = new And(new And(new Entailment(new Entailment(new Variable("c"), new Variable("a")), new Not(new Variable("b"))), new Entailment(new Not(new Variable("c")),
                new And(new Or(new Variable("b"), new Variable("a")), new Not(new Variable("a"))))), new Or(new Variable("b"), new Variable("c")));
        assertEquals(e, ExpressionParser.parse("((c->a)->!b)&(!c->(b|a)&!a)&(b|c)"));

    }
}
