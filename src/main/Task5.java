package main;

import kripke.Model;
import kripke.ModelCreator;
import parser.ExpressionParser;
import parser.ParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Task5 {
    public static void main(String[] args) throws FileNotFoundException, ParserException {
        if (args.length < 1) {
            System.err.println("First argument - file!");
            return;
        }
        Scanner in = new Scanner(new File(args[0]));
        PrintWriter out = new PrintWriter(System.out);
        Model ans = ModelCreator.create(ExpressionParser.parse(in.nextLine()));
        if (ans == null) {
            System.out.println("Истинно");
        } else {
            System.out.println(ans);
        }
    }

}