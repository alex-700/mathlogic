package kripke;

import expressions.Expression;
import expressions.Variable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ModelCreator {

    public static Model create(Expression expression) {
        HashSet<Variable> variables = new HashSet<>();
        expression.getVariables(variables);
        List<Variable> vars = new ArrayList<>(variables);
        for (int i = 1; i <= 2 * variables.size(); i++) {
            List<Model> models = generate(vars, i);
            //System.err.println(models.size());
            for (Model model : models) {
                if (!model.isForced(expression)) {
                    return model;
                }
            }
        }
        return null;
    }

    public static List<Model> generate(List<Variable> variables, int depth) {
        int n = 1 << variables.size();
        List<Model> ans = new ArrayList<>();
        if (variables.size() <= 2 || depth <= 1) {
            for (int startNum = 0; startNum < n; startNum++) {
                ans.addAll(generate(startNum, variables, 0, 0, depth));
            }
        } else {
            for (int startNum = 0; startNum < n; startNum++) {
                ans.addAll(generate(startNum, variables, variables.size(), 0, depth));
            }
        }
        return ans;
    }

    public static List<Model> generate(int startNum, List<Variable> variables, int used, int depth, int maxDepth) {
        int count = variables.size() - Integer.bitCount(startNum);

        int[] x = new int[count];
        int head = 0;
        for (int i = 0; i < variables.size(); i++) {
            if ((startNum & (1 << i)) == 0) {
                x[head++] = i;
            }
        }

        int n = 1 << count;
        List<Model> ans = new ArrayList<>();
        neighbours:
        for (int maskNeighbours = 0; maskNeighbours < (depth == maxDepth ? 1 : (1 << n)); maskNeighbours++) {
            List<List<Model>> list = new ArrayList<>();
            for (int maskVertex = 0; maskVertex < n; maskVertex++) {
                if ((maskNeighbours & (1 << maskVertex)) != 0) {
                    int start = startNum;
                    for (int i = 0; i < count; i++) {
                        if ((maskVertex & (1 << i)) != 0) {
                            start |= (1 << x[i]);
                        }
                    }
                    if (start == startNum) {
                        if (used >= variables.size() - 1) continue neighbours;
                        list.add(generate(start, variables, used + 1, depth + 1, maxDepth));
                    } else {
                        list.add(generate(start, variables, used, depth + 1, maxDepth));
                    }
                }
            }
            merge(startNum, list, new int[list.size()], 0, variables, ans);
        }
        return ans;
    }

    public static void merge(int start, List<List<Model>> list, int[] a, int x, List<Variable> variables, List<Model> ans) {
        if (x == a.length) {
            World root = new World(start, variables);
            for (int i = 0; i < list.size(); i++) {
                root.addChild(list.get(i).get(a[i]).getRootWorld()); // todo: copy
            }
            ans.add(new Model(root));
        } else {
            for (int i = 0; i < list.get(x).size(); i++) {
                a[x] = i;
                merge(start, list, a, x + 1, variables, ans);
            }
        }
    }


}