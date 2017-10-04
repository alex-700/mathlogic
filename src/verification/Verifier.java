package verification;

import expressions.Expression;
import proofs.Proof;

import java.util.List;

public interface Verifier {
    Proof verify(List<Expression> expressions);
}
