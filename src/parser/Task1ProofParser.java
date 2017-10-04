package parser;

import expressions.Expression;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Task1ProofParser implements ProofParser {

    @Override
    public List<Expression> parse(File f) throws FileNotFoundException, ParserException {
        Scanner in = new Scanner(f);
        List<Expression> expressions = new ArrayList<>();
        for (; true; ) {
            String v;
            try {
                v = in.nextLine();
            } catch (Exception e) {
                break;
            }
            expressions.add(ExpressionParser.parse(v));
        }
        in.close();
        return expressions;
    }
}