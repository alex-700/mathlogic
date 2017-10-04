package expressions;

import java.util.Map;

public class Entailment extends AbstractBinaryExpression implements Expression {

    public Entailment(Expression left, Expression right) {
        super(left, right, "Entailment", "->");
    }

    @Override
    public Expression fillVariables(Map<String, Expression> map) {
        return new Entailment(left.fillVariables(map), right.fillVariables(map));
    }
}
