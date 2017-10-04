package proof_assumptions;

import proofs.StatementType;

public class Assumption implements StatementType {
    private int x;

    public Assumption(int x) {
        this.x = x;
    }

    @Deprecated
    public String toString() {
        return " (доп " + (x + 1) + ")";
    }
}