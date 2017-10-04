package kripke;

import expressions.*;

import java.util.*;
import java.util.stream.Collectors;

public class World {
    private List<World> children;
    private Set<Variable> forcedVariables;
    private Map<Expression, Boolean> forcedExpression;

    public World() {
        children = new ArrayList<>();
        forcedVariables = new HashSet<>();
        forcedExpression = new HashMap<>();
    }

    public World(int num, List<Variable> list) {
        children = new ArrayList<>();
        forcedVariables = new HashSet<>();
        forcedExpression = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            if ((num & (1 << i)) != 0) {
                forcedVariables.add(list.get(i));
            }
        }
    }

    public void toStringQuick(StringBuilder sb, int depth) {
        for (int i = 0; i < depth; i++) {
            sb.append(' ');
        }
        sb.append('*');
        boolean flag = false;
        for (Variable variable : forcedVariables) {
            if (flag) sb.append(", ");
            sb.append(variable);
            flag = true;
        }
        sb.append('\n');
        for (World w : children) {
            w.toStringQuick(sb, depth + 1);
        }
    }

    public boolean isForced(Expression expression) {
//        if (forcedExpression.containsKey(expression)) return forcedExpression.get(expression);
        boolean ans;
        if (expression instanceof And) {
            ans = isForced(((And) expression).left) && isForced(((And) expression).right);
        } else if (expression instanceof Or) {
            ans = isForced(((Or) expression).left) || isForced(((Or) expression).right);
        } else if (expression instanceof Not) {
            ans = true;
            Queue<World> worlds = new ArrayDeque<>();
            worlds.add(this);
            while (!worlds.isEmpty()) {
                World w = worlds.poll();
                if (w.isForced(((Not) expression).getExpr())) {
                    ans = false;
                    break;
                }
                worlds.addAll(w.children.stream().collect(Collectors.toList()));
            }
        } else if (expression instanceof Entailment) {
            ans = true;
            Queue<World> worlds = new ArrayDeque<>();
            worlds.add(this);
            while (!worlds.isEmpty()) {
                World w = worlds.poll();
                if (w.isForced(((Entailment) expression).left) && !w.isForced(((Entailment) expression).right)) {
                    ans = false;
                    break;
                }
                worlds.addAll(w.children.stream().collect(Collectors.toList()));
            }
        } else if (expression instanceof Variable) {
            ans = forcedVariables.contains(expression);
        } else {
            throw new IllegalStateException();
        }
//        forcedExpression.put(expression, ans);
        return ans;
    }

    public void addChild(World child) {
        children.add(child);
    }

    private void addForcedVariable(Variable var) {
        forcedVariables.add(var);
    }

    public World copy() {
        World ret = new World();
        for (Variable var : forcedVariables) {
            ret.addForcedVariable(var);
        }
        for (World child : children) {
            ret.addChild(child.copy());
        }
        return ret;
    }
}