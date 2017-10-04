package expressions;

import java.util.Map;

public class Not extends AbstractUnaryExpression implements Expression {

    public Not(Expression expr) {
        super(expr, "Not", "!");
    }

    @Override
    public String getName() {
        return "Not";
    }

    @Override
    public boolean matches(Expression ex, Map<String, Expression> map) {
        return ex.getName().equals("Not")
                && expr.matches(((AbstractUnaryExpression) ex).expr, map);
    }

    @Override
    public Expression fillVariables(Map<String, Expression> map) {
        return new Not(expr.fillVariables(map));
    }
}
