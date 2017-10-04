package expressions;

import java.util.Map;

public class And extends AbstractBinaryExpression implements Expression {

    public And(Expression left, Expression right) {
        super(left, right, "And", "&");
    }

    @Override
    public Expression fillVariables(Map<String, Expression> map) {
        return new And(left.fillVariables(map), right.fillVariables(map));
    }
}
