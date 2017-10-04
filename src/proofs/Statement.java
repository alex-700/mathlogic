package proofs;

import expressions.Expression;

public class Statement {
    private Expression expression;
    private StatementType type;

    public Statement(Expression expression) {
        this.expression = expression;
    }

    public Statement(Expression expression, StatementType type) {
        this.expression = expression;
        this.type = type;
    }

    public StatementType getType() {
        return type;
    }

    public void setType(StatementType type) {
        this.type = type;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Statement statement = (Statement) o;

        if (expression != null ? !expression.equals(statement.expression) : statement.expression != null) return false;
        if (type != null ? !type.equals(statement.type) : statement.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = expression != null ? expression.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    public boolean proofed() {
        return type != null || (type instanceof ModusPonens && ((ModusPonens) type).first != -1);
    }

    @Override
    public String toString() {
        return expression.toString() + type.toString();
    }
}