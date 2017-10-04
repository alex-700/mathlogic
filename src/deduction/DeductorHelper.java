package deduction;

import expressions.Entailment;
import expressions.Expression;

import java.util.ArrayList;
import java.util.List;

class DeductorHelper {

    static List<Expression> getDeductionForMP(Expression a, Expression f, Expression s, Expression m) {
        List<Expression> list = new ArrayList<>();
        list.add(new Entailment (new Entailment (a, f), new Entailment (new Entailment (a, new Entailment (f, m)), new Entailment (a, m))));
        list.add(new Entailment (new Entailment (a, new Entailment (f, m)), new Entailment (a, m)));
        list.add(new Entailment(a, m));
        return list;
    }

    static List<Expression> getDeductionAEA(Expression a) {
        List<Expression> list = new ArrayList<>();
        list.add(new Entailment (a, new Entailment (a, a)));
        list.add(new Entailment (new Entailment (a, new Entailment (a, a)), new Entailment (new Entailment (a, new Entailment (new Entailment (a, a), a)), new Entailment (a, a))));
        list.add(new Entailment (new Entailment (a, new Entailment (new Entailment (a, a), a)), new Entailment (a, a)));
        list.add(new Entailment (a, new Entailment (new Entailment (a, a), a)));
        list.add(new Entailment (a, a));
        return list;
    }

    static List<Expression> getDeductionAxiom(Expression a, Expression b) {
        List<Expression> list = new ArrayList<>();
        list.add(a);
        list.add(new Entailment (a, new Entailment (b, a)));
        list.add(new Entailment (b, a));
        return list;
    }

}