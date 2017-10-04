package verification;

import expressions.Expression;
import proof_assumptions.Assumption;
import proofs.Axiom;
import proofs.Proof;
import proofs.Statement;

import java.util.List;

public class HashVerifier implements Verifier {

    @Override
    public Proof verify(List<Expression> expressions) {
        Proof proof = new Proof();

        main:
        for (Expression expr : expressions) {
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

        return proof;
    }
}