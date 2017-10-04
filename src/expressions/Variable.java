package expressions;

import java.util.HashSet;
import java.util.Map;

public class Variable implements Expression {

    private String name;
    private Priority priority;
    public Variable(String name) {
        super();
        this.name = name;
        this.priority = Priority.Variable;
    }

    public String getVariableName() {
        return name;
    }

    @Override
    public boolean isBinary() {
        return false;
    }

    @Override
    public String toJavaCode() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Variable && ((Variable) obj).name.equals(name);
    }

    @Override
    public String getName() {
        return "Variable";
    }

    @Override
    public boolean matches(Expression expr, Map<String, Expression> map) {
        if (map.containsKey(name)) {
            return map.get(name).equals(expr);
        } else {
            map.put(name, expr);
            return true;
        }
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    public String toString() {
        return name;
    }

    @Override
    public void getVariables(HashSet<Variable> hashSet) {
        hashSet.add(this);
    }

    @Override
    public Expression fillVariables(Map<String, Expression> map) {
        return map.get(name);
    }
}
