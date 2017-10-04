package deduction;

import proofs.Proof;

public interface Deductor {
    Proof deduct(Proof proof);
}