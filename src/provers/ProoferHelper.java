package provers;

import deduction.DeductionsExpressions;
import deduction.HashVerifier;
import deduction.Verifier;
import expressions.*;
import parser.ExpressionParser;
import parser.ParserException;
import proofs.Proof;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ProoferHelper {
    public static Proof getAssumptionsProofFromExpression(Expression expression, List<Expression> assumptions) {
        Verifier verifier = new HashVerifier();
        List<Expression> expressions = new ArrayList<>();
        expressions.add(expression);
        return verifier.verify(new DeductionsExpressions(expressions, assumptions, expression));
    }

    public static Proof getMegaLemma(Proof proof1, Proof proof2, Map<String, Expression> map) throws FileNotFoundException {
        Proof proof = proof1.merge(proof2);
        Scanner in = new Scanner(new File("lemmas/megalemma"));
        List<Expression> list = new ArrayList<>();
        while (true) {
            String s;
            try {
                s = in.nextLine();
            } catch (Exception e) {
                break;
            }
            try {
                list.add(ExpressionParser.parse(s).fillVariables(map));
            } catch (ParserException e) {
                e.printStackTrace();
            }
        }
        proof.addExpressionList(list);
        return proof;
    }

    public static void doNotNotProof(Proof proof) throws FileNotFoundException {
        Scanner in = new Scanner(new File("lemmas/notnot"));
        Map<String, Expression> map = new HashMap<>();
        map.put("a", proof.getProofedExpression());
        List<Expression> list = new ArrayList<>();

        while (true) {
            String s;
            try {
                s = in.nextLine();
            } catch (Exception e) {
                break;
            }
            try {
                list.add(ExpressionParser.parse(s).fillVariables(map));
            } catch (ParserException e) {
                e.printStackTrace();
            }
        }
        in.close();
        proof.addExpressionList(list);
    }

    public static int getNumberOfLemma(Expression expr, Expression pLeft, Expression pRight) {
        if (expr instanceof AbstractBinaryExpression) {
            Expression left = ((AbstractBinaryExpression) expr).left;
            Expression right = ((AbstractBinaryExpression) expr).right;
            boolean lepl = left.equals(pLeft);
            boolean repr = right.equals(pRight);
            if (expr instanceof And) {
                if (lepl) {
                    if (repr) {
//                        P, L |- P&L
                        return 1;
                    } else {
//                        P, !L |- !(P&L)
                        return 2;
                    }
                } else {
                    if (repr) {
//                        !P, L |- !(P&L)
                        return 3;
                    } else {
//                        !P, !L |- !(P&L)
                        return 4;
                    }
                }
            } else if (expr instanceof Or) {
                if (lepl) {
                    if (repr) {
//                        P, L |- P|L
                        return 5;
                    } else {
//                        P, !L |- P|L
                        return 6;
                    }
                } else {
                    if (repr) {
//                        !P, L |- P|L
                        return 7;
                    } else {
//                        !P, !L |- !(P|L)
                        return 8;
                    }
                }
            } else if (expr instanceof Entailment) {
                if (lepl) {
                    if (repr) {
//                        P, L |- P->L
                        return 9;
                    } else {
//                        P, !L |- !(P->L)
                        return 10;
                    }
                } else {
                    if (repr) {
//                        !P, L |- P->L
                        return 11;
                    } else {
//                        !P, !L |- P->L
                        return 12;
                    }
                }
            } else {
                throw new RuntimeException("Wrong type of expression");
            }
        } else {
            throw new RuntimeException("Wrong type of expression");
        }
    }

    public static Proof applyLemma(int type, Proof proof1, Proof proof2, Map<String, Expression> map) throws FileNotFoundException {
        Scanner in = new Scanner(new File("lemmas/"+String.valueOf(type)));
        List<Expression> list = new ArrayList<>();

        while (true) {
            String s;
            try {
                s = in.nextLine();
            } catch (Exception e) {
                break;
            }
            try {
                list.add(ExpressionParser.parse(s).fillVariables(map));
            } catch (ParserException e) {
                e.printStackTrace();
            }
        }
        in.close();
        Proof proof = proof1.merge(proof2);
        proof.addExpressionList(list);
        return proof;
    }
}