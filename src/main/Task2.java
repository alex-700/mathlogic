package main;

import deduction.Deductor;
import deduction.HashVerifier;
import deduction.Task2Deductor;
import deduction.Verifier;
import parser.ParserException;
import parser.Task2ProofParser;

import java.io.File;
import java.io.FileNotFoundException;

public class Task2 {
    public static void main(String[] args) throws FileNotFoundException, ParserException {
        Task2ProofParser parser = new Task2ProofParser();
        Verifier verifier = new HashVerifier();
        Deductor deductor = new Task2Deductor();
        //verifier.verify(parser.parse(new File("proof0.in"))).print();
        deductor.deduct(deductor.deduct(verifier.verify(parser.parse(new File("allLemma.in"))))).printForTask2(new File("allLemma.out"));

//        System.out.println(ExpressionParser.parse("b").toJavaCode());
//        System.out.println(ExpressionParser.parse("a->b->a").toJavaCode());
//        System.out.println(ExpressionParser.parse("b->a").toJavaCode());

    }
}