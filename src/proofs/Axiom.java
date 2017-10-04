package proofs;

import expressions.Expression;
import parser.ExpressionParser;
import parser.ParserException;

import java.util.HashMap;

public enum Axiom implements StatementType {

    One("a->b->a"),
    Two("(a->b)->(a->b->c)->(a->c)"),
    Three("a->b->a&b"),
    Four("a&b->a"),
    Five("a&b->b"),
    Six("a->a|b"),
    Seven("b->a|b"),
    Eight("(a->c)->(b->c)->(a|b->c)"),
    Nine("(a->b)->(a->!b)->!a"),
    Ten("!!a->a");

    public Expression expr;

    Axiom(String s) {
        try {
            this.expr = ExpressionParser.parse(s);
        } catch (ParserException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean match(Expression ex) {
        HashMap<String, Expression> map = new HashMap<String, Expression>();
        return expr.matches(ex, map);
    }

    @Override
    public String toString() {
        return " (" + "Сх. акс. " + (ordinal() + 1) + ")";
    }
}
