package kripke;

import expressions.*;

public class Model {
    private World rootWorld;

    public Model(World rootWorld) {
        this.rootWorld = rootWorld;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        rootWorld.toStringQuick(sb, 0);
        return sb.toString();
    }

    public boolean isForced(Expression expression) {
        return rootWorld.isForced(expression);
    }

    public World getRootWorld() {
        return rootWorld;
    }
}