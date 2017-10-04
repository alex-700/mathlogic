package expressions;

import java.util.HashSet;
import java.util.Map;

public abstract class AbstractBinaryExpression implements Expression {
    public Expression left, right;
    protected String name, symbol;
    Priority priority;

    public AbstractBinaryExpression(Expression left, Expression right,
                             String name, String symbol) {
        super();
        this.left = left;
        this.right = right;
        this.name = name;
        this.symbol = symbol;
        this.priority = Priority.valueOf(name);
    }

    @Override
    public String toJavaCode() {
        return String.format("new %s (%s, %s)", name, left.toJavaCode(),
                right.toJavaCode());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((left == null) ? 0 : left.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((right == null) ? 0 : right.hashCode());
        result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Expression) {
            if (!((Expression) obj).isBinary()) {
                return false;
            } else {
                AbstractBinaryExpression abe = (AbstractBinaryExpression) obj;
                return (abe.name.equals(name) && abe.left.equals(left) && abe.right
                        .equals(right));
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean isBinary() {
        return true;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean matches(Expression expr, Map<String, Expression> map) {
        if (!name.equals(expr.getName()))
            return false;
        return left.matches(((AbstractBinaryExpression) expr).left, map)
                && right.matches(((AbstractBinaryExpression) expr).right, map);
    }

    @Override
    public String toString() {
        String sleft = left.toString(), sright = right.toString();

        if (left.getPriority().ordinal() <= getPriority().ordinal()) {
            sleft = "(" + sleft + ")";
        }
        if (right.getPriority().ordinal() <= getPriority().ordinal()) {
            sright = "(" + sright + ")";
        }
        return String.format("%s%s%s", sleft, symbol, sright);
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public void getVariables(HashSet<Variable> hashSet) {
        left.getVariables(hashSet);
        right.getVariables(hashSet);
    }
}
