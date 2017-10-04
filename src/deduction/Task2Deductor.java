package deduction;

import expressions.Entailment;
import expressions.Expression;
import proof_assumptions.Assumption;
import proofs.ModusPonens;
import proofs.Proof;
import proofs.Statement;

import java.util.ArrayList;
import java.util.List;

public class Task2Deductor implements Deductor {

    @Override
    public Proof deduct(Proof proof) {
        if (!proof.hasAssumptions()) {
            return proof;
        } else {
            Expression delAssumptions = proof.getAssumptions().expressions.get(proof.getAssumptions().expressions.size() - 1);
            proof.getAssumptions().expressions.remove(proof.getAssumptions().expressions.size() - 1);
            proof.getAssumptions().expressionMap.remove(delAssumptions);

            List<Statement> list = proof.getStatements();
            List<Expression> expressions = new ArrayList<>();
            for (Statement st : list) {
                if (st.getExpression().equals(delAssumptions)) {
                    expressions.addAll(DeductorHelper.getDeductionAEA(delAssumptions));
                } else if (st.getType().getClass().isEnum() || st.getType() instanceof Assumption) {
                    expressions.addAll(DeductorHelper.getDeductionAxiom(st.getExpression(), delAssumptions));
                } else if (st.getType() instanceof ModusPonens) {
                    Expression f = list.get(((ModusPonens)st.getType()).getFirst()).getExpression();
                    Expression s = list.get(((ModusPonens) st.getType()).getSecond()).getExpression();
                    expressions.addAll(DeductorHelper.getDeductionForMP(delAssumptions, f, s, st.getExpression()));
                }
            }

            Verifier verifier = new HashVerifier();
            Proof p = verifier.verify(new DeductionsExpressions(expressions, proof.getAssumptions().expressions, new Entailment(delAssumptions, proof.getProofedExpression())));
            proof.addAssumption(delAssumptions);
            return p;
        }
    }
}