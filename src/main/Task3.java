package main;


import expressions.Expression;
import parser.ExpressionParser;
import parser.ParserException;
import provers.Prover;
import provers.Task3Prover;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Task3 {
    public static void main(String[] args) throws ParserException, FileNotFoundException {
        Scanner in = new Scanner(System.in);
        Prover prover = new Task3Prover();
        Expression expr = ExpressionParser.parse(in.nextLine());
        long start = System.currentTimeMillis();
        prover.getProof(expr).print(new File("testMax.out"));
        System.out.println((System.currentTimeMillis() - start) + " ms");
    }

}