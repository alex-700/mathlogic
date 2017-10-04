package deduction;

import expressions.Expression;

import java.util.List;

public class DeductionsExpressions {
    List<Expression> expressions, assumptions;
    Expression mainStatement;

    public DeductionsExpressions(List<Expression> expressions, List<Expression> assumptions, Expression mainStatement) {
        this.expressions = expressions;
        this.assumptions = assumptions;
        this.mainStatement = mainStatement;
    }
}