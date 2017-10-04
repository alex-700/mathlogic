package expressions;

import java.util.Map;

public class Or extends AbstractBinaryExpression implements Expression {

    public Or(Expression left, Expression right) {
        super(left, right, "Or", "|");
    }

    @Override
    public Expression fillVariables(Map<String, Expression> map) {
        return new Or(left.fillVariables(map), right.fillVariables(map));
    }
}
