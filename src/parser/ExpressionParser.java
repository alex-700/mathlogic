package parser;

import expressions.*;

public class ExpressionParser {
    private static String s;
    private static int index;

    public static Expression parse(String str) throws ParserException {
        s = str;
        index = 0;
        skipSpace();
        if (s.length() == 0) {
            throw new ParserException("Empty string");
        }
        Expression ans = parseEntailment(parseOr(parseAnd(parseFactor())));
        skipSpace();
        char c = getChar();
        if (c != '\n') {
            if (c == ')') {
                throw new ParserException("");
            }
            System.out.println("c = /" + c + "/");
            throw new ParserException("Unexpected symbol at " + index);
        }
        return ans;
    }

    private static boolean isVariable(char c) {
        return (('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z'));
    }

    private static String getVariable(char c) {
        StringBuilder sb = new StringBuilder();
        sb.append(c);
        char c1 = getChar();
        while ('0' <= c1 && c1 <= '9') {
            sb.append(c1);
            c1 = getChar();
        }
        returnChar();
        return sb.toString();
    }

    private static void skipSpace() {
        while (s.length() > index && Character.isWhitespace(s.charAt(index)))
            index++;
    }

    private static char getChar() {
        if (s.length() > index) {
            return s.charAt(index++);
        } else {
            if (s.length() == index) index++;
            return '\n';
        }
    }

    private static void returnChar() {
        if (index <= s.length()) index--;
    }

    private static Expression parseFactor() throws ParserException {
        skipSpace();
        char c = getChar();
        if (c == '!') {
            return new Not(parseFactor());
        }
        if (isVariable(c)) {
            return new Variable(getVariable(c));
        }
        if (c == '(') {
            Expression res = parseEntailment(parseOr(parseAnd(parseFactor())));
            skipSpace();
            if (getChar() != ')') {
                throw new ParserException("Brackets");
            }
            return res;
        }
        if (c == '\n') {
            throw new ParserException("Unexpected symbol at " + index);
        }
        throw new ParserException("Unexpected symbol at " + index);
    }

    private static Expression parseOr(Expression left) throws ParserException {
        skipSpace();
        char c = getChar();
        if (c != '|') {
            returnChar();
            return left;
        }
        Expression right = parseAnd(parseFactor());
        return parseOr(new Or(left, right));
    }

    private static Expression parseAnd(Expression left) throws ParserException {
        skipSpace();
        char c = getChar();
        if (c != '&') {
            returnChar();
            return left;
        } else {
            Expression right = parseFactor();
            return parseAnd(new And(left, right));
        }
    }

    private static Expression parseEntailment(Expression left) throws ParserException {
        skipSpace();
        char c = getChar();
        if (c != '-') {
            returnChar();
            return left;
        } else {
            c = getChar();
            if (c != '>') {
                throw new ParserException("Unexpected symbol at " + index);
            }
            return new Entailment(left, parseEntailment(parseOr(parseAnd(parseFactor()))));
        }
    }
}
