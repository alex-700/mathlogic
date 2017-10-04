package parser;

import deduction.DeductionsExpressions;
import expressions.Expression;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Task2ProofParser implements ProofParserD {

    @Override
    public DeductionsExpressions parse(File f) throws FileNotFoundException, ParserException {
        Scanner in = new Scanner(f);
        List<Expression> expressions = new ArrayList<>();
        List<Expression> assumptions = new ArrayList<>();
        String a = in.nextLine();
        String as = a.split("\\|-")[0];
        String main = a.split("\\|-")[1];
        System.out.println(as);
        for (String x : as.split(",")) {
            System.out.println(x);
            System.out.flush();
            assumptions.add(ExpressionParser.parse(x));
        }

        for (; true; ) {
            String v;
            try {
                v = in.nextLine();
            } catch (Exception e) {
                break;
            }
            if (v.equals("")) continue;
            expressions.add(ExpressionParser.parse(v));
        }
        in.close();
        return new DeductionsExpressions(expressions, assumptions, ExpressionParser.parse(main));
    }
}