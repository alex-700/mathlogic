package proofs;

import expressions.Entailment;
import expressions.Expression;
import proof_assumptions.Assumption;
import proof_assumptions.Assumptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Proof {
    private List<Statement> statements;
    private Map<Expression, Integer> expressionMap;
    private HashMap<Expression, Set<Integer>> rightMap;
    private Expression proofedExpression = null;
    private Assumptions assumptions;
    private String message = null;

    public Proof() {
        statements = new ArrayList<>();
        expressionMap = new HashMap<>();
        rightMap = new HashMap<>();
        assumptions = new Assumptions();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean hasMessage() {
        return message != null;
    }

    public void addExpressionList(List<Expression> exprs) {
        main:
        for (Expression expr : exprs) {
            Statement current = new Statement(expr);
            for (Axiom ax : Axiom.values()) {
                if (ax.match(expr)) {
                    current.setType(ax);
                    addStatement(current);
                    continue main;
                }
            }
            Assumption assumption = checkAssumption(expr);
            if (assumption != null) {
                current.setType(assumption);
            } else {
                current.setType(check(expr));
            }
            addStatement(current);
        }
        setProofedExpression(exprs.get(exprs.size() - 1));
    }

    public Proof merge(Proof proof) {
        addExpressionList(proof.getExpressions());
        return this;
    }

    public List<Expression> getExpressions() {
        List<Expression> list = new ArrayList<>();
        for (Statement st : statements) {
            list.add(st.getExpression());
        }
        return list;
    }

    public Expression getProofedExpression() {
        return proofedExpression;
    }

    public void setProofedExpression(Expression proofedExpression) {
        this.proofedExpression = proofedExpression;
    }

    public Assumptions getAssumptions() {
        return assumptions;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public void addAssumption(Expression expression) {
        assumptions.add(expression);
    }

    public void addStatement(Statement statement) {
        statements.add(statement);
        expressionMap.put(statement.getExpression(), statements.size() - 1);
        if (statement.getExpression() instanceof Entailment) {
            if (!rightMap.containsKey(((Entailment) statement.getExpression()).right)) {
                rightMap.put(((Entailment) statement.getExpression()).right, new TreeSet<>());
            }
            rightMap.get(((Entailment) statement.getExpression()).right).add(statements.size() - 1);
        }
    }

    public Assumption checkAssumption(Expression expr) {
        if (assumptions.expressionMap.containsKey(expr)) {
            return new Assumption(assumptions.expressionMap.get(expr));
        }
        return null;
    }

    public ModusPonens check(Expression expr) {
        if (rightMap.containsKey(expr)) {
            for (int num : rightMap.get(expr)) {
                Statement st = statements.get(num);
                Expression left = ((Entailment) st.getExpression()).left;
                if (st.proofed()) {
                    if (expressionMap.containsKey(left) && statements.get(expressionMap.get(left)).proofed())
                        return new ModusPonens(expressionMap.get(left), num);
                }
            }
        }
        return new ModusPonens();
    }

    public boolean hasAssumptions() {
        return assumptions.expressions.size() != 0;
    }

    public void print(File f) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(f)) {
            if (message == null) {
                for (int i = 0; i < statements.size(); i++) {
                    out.println(String.format("(%d) %s", i + 1, statements.get(i)));
                }
            } else {
                out.println(message);
            }
        }
    }

    public void print() {
        try (PrintWriter out = new PrintWriter(System.out)) {
            for (int i = 0; i < statements.size(); i++) {
                out.println(String.format("(%d) %s", i + 1, statements.get(i)));
            }
        }
    }

    public void printExpressions(File f) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(f)) {
            for (Statement statement : statements) {
                out.println(statement.getExpression());
            }
        }
    }

    public void printHeader(File f) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(f)) {
            printHeaderToPrintWriter(out);
        }
    }

    public void printHeader() {
        try (PrintWriter out = new PrintWriter(System.out)) {
            printHeaderToPrintWriter(out);
        }
    }

    private void printHeaderToPrintWriter(PrintWriter out) {
        StringBuilder sb = new StringBuilder();
        if (hasAssumptions()) {
            sb.append(assumptions.expressions.get(0));
        }
        for (int i = 1; i < assumptions.expressions.size(); i++) {
            sb.append(",").append(assumptions.expressions.get(i));
        }
        sb.append("|-");
        sb.append(proofedExpression);
        out.println(sb);
    }

    public void printForTask2(File f) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(f)) {
            printHeaderToPrintWriter(out);
            for (Statement statement : statements) {
                out.println(statement.getExpression());
            }
        }
    }

}