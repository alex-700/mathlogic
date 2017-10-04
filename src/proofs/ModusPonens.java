package proofs;

public class ModusPonens implements StatementType {
    public int first, second;

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    ModusPonens() {
        first = -1;
        second = -1;
    }

    ModusPonens(int first, int second) {
        this.first = first;
        this.second = second;
    }


    @Override
    public String toString() {
        if (first == -1) {
            return " (Не доказано)";
        } else {
            return " (M.P. "  + (first + 1) + " " + (second + 1) + ")";
        }
    }

}