package provers;

import expressions.Expression;
import expressions.Variable;
import proofs.Proof;

import java.util.List;

public interface Prover {
    Proof getProof(Expression expression, List<Expression> assumptions);
    Proof getProof(Expression expression);
    Proof getProof(Expression expression, int count, int maxCount, List<Expression> assumptions, List<Variable> variables);
}
