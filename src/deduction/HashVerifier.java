package deduction;

import expressions.Expression;
import proof_assumptions.Assumption;
import proofs.Axiom;
import proofs.Proof;
import proofs.Statement;

public class HashVerifier implements Verifier {

    @Override
    public Proof verify(DeductionsExpressions deductionsExpressions) {
        Proof proof = new Proof();
        for (Expression assumption : deductionsExpressions.assumptions) {
            proof.addAssumption(assumption);
        }

        main:
        for (Expression expr : deductionsExpressions.expressions) {
            Statement current = new Statement(expr);
            for (Axiom ax : Axiom.values()) {
                if (ax.match(expr)) {
                    current.setType(ax);
                    proof.addStatement(current);
                    continue main;
                }
            }
            Assumption assumption = proof.checkAssumption(expr);
            if (assumption != null) {
                current.setType(assumption);
            } else {
                current.setType(proof.check(expr));
            }
            proof.addStatement(current);
        }
        proof.setProofedExpression(deductionsExpressions.mainStatement);
        return proof;
    }
}