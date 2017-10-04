package proof_assumptions;

import expressions.Expression;

import java.util.*;

public class Assumptions {
    public List<Expression> expressions;
    public Map<Expression, Integer> expressionMap;

    public Assumptions() {
        expressions = new ArrayList<>();
        expressionMap = new HashMap<>();
    }

    public void add(Expression expr) {
        expressions.add(expr);
        expressionMap.put(expr, expressions.size() - 1);
    }
}