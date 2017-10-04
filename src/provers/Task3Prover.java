package provers;

import deduction.Deductor;
import deduction.Task2Deductor;
import expressions.AbstractBinaryExpression;
import expressions.Expression;
import expressions.Not;
import expressions.Variable;
import proofs.Proof;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Task3Prover implements Prover {

    @Override
    public Proof getProof(Expression expression, List<Expression> assumptions) {
        if (expression instanceof AbstractBinaryExpression) {
            Proof proofLeft = getProof(((AbstractBinaryExpression) expression).left, assumptions);
            Proof proofRight = getProof(((AbstractBinaryExpression) expression).right, assumptions);
            Expression left = ((AbstractBinaryExpression) expression).left;
            Expression right = ((AbstractBinaryExpression) expression).right;
            if (new Not(proofLeft.getProofedExpression()).equals(left)) {
                try {
                    ProoferHelper.doNotNotProof(proofLeft);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (new Not(proofRight.getProofedExpression()).equals(right)) {
                try {
                    ProoferHelper.doNotNotProof(proofRight);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            Proof proof = null;
            try {
                HashMap<String, Expression> map = new HashMap<>();
                map.put("a", ((AbstractBinaryExpression) expression).left);
                map.put("b", ((AbstractBinaryExpression) expression).right);
                proof = ProoferHelper.applyLemma(ProoferHelper.getNumberOfLemma(expression, proofLeft.getProofedExpression(), proofRight.getProofedExpression()), proofLeft, proofRight, map);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return proof;
        } else if (expression instanceof Not) {
            Expression nextExpression = ((Not) expression).getExpr();
            if (nextExpression instanceof AbstractBinaryExpression) {
                return getProof(nextExpression, assumptions);
            } else if (nextExpression instanceof Not) {
                Proof proof = getProof(((Not) nextExpression).getExpr(), assumptions);
                try {
                    ProoferHelper.doNotNotProof(proof);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return proof;
            } else if (nextExpression instanceof Variable) {
                if (assumptions.contains(nextExpression)) {
                    return ProoferHelper.getAssumptionsProofFromExpression(nextExpression, assumptions);
                } else if (assumptions.contains(new Not(nextExpression))) {
                    return ProoferHelper.getAssumptionsProofFromExpression(new Not(nextExpression), assumptions);
                } else {
                    throw new RuntimeException("Wrong assumptions");
                }
            }
        } else if (expression instanceof Variable) {
            if (assumptions.contains(expression)) {
                return ProoferHelper.getAssumptionsProofFromExpression(expression, assumptions);
            } else if (assumptions.contains(new Not(expression))) {
                return ProoferHelper.getAssumptionsProofFromExpression(new Not(expression), assumptions);
            } else {
                throw new RuntimeException("Wrong assumptions");
            }
        } else {
            throw new RuntimeException("Wrong type of expression");
        }
        return null;
    }

    @Override
    public Proof getProof(Expression expression) {
        HashSet<Variable> hashSet = new HashSet<>();
        expression.getVariables(hashSet);
        int countVariables = hashSet.size();

        return getProof(expression, 0, countVariables, new ArrayList<Expression>(), new ArrayList<>(hashSet));
    }

    public String getMessgage(List<Expression> assumptions, List<Variable> variables) {
        StringBuilder sb = new StringBuilder();
        sb.append("Высказывание ложно при ");
        for (int i = 0; i < variables.size(); i++) {
            if (i != 0) sb.append(", ");
            sb.append(variables.get(i).getVariableName());
            sb.append("=");
            if (i < assumptions.size()) {
                sb.append((assumptions.get(i) instanceof Not) ? "Л" : "И");
            } else {
                sb.append("И");
            }
        }
        return sb.toString();
    }

    @Override
    public Proof getProof(Expression expression, int count, int maxCount, List<Expression> assumptions, List<Variable> variables) {
        if (count == maxCount) {
            return getProof(expression, assumptions);
        } else {
            assumptions.add(variables.get(count));
            Proof proof1 = getProof(expression, count + 1, maxCount, assumptions, variables);
            assumptions.remove(assumptions.size() - 1);
            if (proof1.hasMessage()) {
                return proof1;
            }
            if (!proof1.getProofedExpression().equals(expression)) {
                proof1.setMessage(getMessgage(assumptions, variables));
                return proof1;
            }

            assumptions.add(new Not(variables.get(count)));
            Proof proof2 = getProof(expression, count + 1, maxCount, assumptions, variables);
            assumptions.remove(assumptions.size() - 1);
            if (proof2.hasMessage()) {
                return proof2;
            }
            if (!proof2.getProofedExpression().equals(expression)) {
                proof2.setMessage(getMessgage(assumptions, variables));
                return proof2;
            }

            Deductor deductor = new Task2Deductor();
            proof1 = deductor.deduct(proof1);
            proof2 = deductor.deduct(proof2);
            HashMap<String, Expression> map = new HashMap<>();
            map.put("a", variables.get(count));
            map.put("b", expression);
            Proof proof = null;
            try {
                proof = ProoferHelper.getMegaLemma(proof1, proof2, map);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return proof;
        }
    }
}