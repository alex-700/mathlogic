package expressions;

import java.util.HashSet;

public abstract class AbstractUnaryExpression implements Expression {
    protected Expression expr;
    protected String name, symbol;
    Priority priority;

    public AbstractUnaryExpression(Expression expr, String name, String symbol) {
        super();
        this.expr = expr;
        this.name = name;
        this.symbol = symbol;
        this.priority = Priority.valueOf(name);
    }

    @Override
    public boolean isBinary() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String toJavaCode() {
        // TODO Auto-generated method stub
        return String.format("new %s (%s)", name, expr.toJavaCode());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((expr == null) ? 0 : expr.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Expression) {
            if (obj instanceof Variable || ((Expression) obj).isBinary()) {
                return false;
            } else {
                AbstractUnaryExpression aue = (AbstractUnaryExpression) obj;
                return (aue.name.equals(name) && aue.expr.equals(expr));
            }
        } else {
            return false;
        }
    }

    public String toString() {
        if (getPriority().ordinal() > expr.getPriority().ordinal()) {
            return String.format("%s(%s)", symbol, expr.toString());
        } else {
            return String.format("%s%s", symbol, expr.toString());
        }
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public void getVariables(HashSet<Variable> hashSet) {
        expr.getVariables(hashSet);
    }

    public Expression getExpr() {
        return expr;
    }
}
