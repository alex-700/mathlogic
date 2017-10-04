package main;

import parser.ParserException;
import parser.Task1ProofParser;
import verification.HashVerifier;
import verification.Verifier;

import java.io.File;
import java.io.FileNotFoundException;

public class Task1 {
    public static void main(String[] args) throws ParserException, FileNotFoundException {
        Task1ProofParser parser = new Task1ProofParser();
        Verifier verifier = new HashVerifier();
        verifier.verify(parser.parse(new File("good6.in"))).print(new File("good6.out"));
        //verifier.verify(parser.parse(new File("maxtest1.in"))).print(new File("ans2.out"));
    }
}