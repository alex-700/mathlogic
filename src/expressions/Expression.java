package expressions;

import java.util.HashSet;
import java.util.Map;

public interface Expression {
    String toJavaCode();

    boolean isBinary();

    String getName();

    boolean matches(Expression expr, Map<String, Expression> map);

    Priority getPriority();

    String toString();

    void getVariables(HashSet<Variable> hashSet);

    Expression fillVariables(Map<String, Expression> map);
}
