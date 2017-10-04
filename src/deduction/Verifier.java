package deduction;

import proofs.Proof;

public interface Verifier {
    Proof verify(DeductionsExpressions deductionsExpressions);
}