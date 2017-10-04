package main;

import parser.ParserException;

import java.io.File;
import java.io.FileNotFoundException;

public class Task5folder {
    public static void main(String[] args) throws FileNotFoundException, ParserException {
        File dir = new File("tests/HW5");
        for (String name : dir.list()) {
            System.out.println(name);
            Task5.main(new String[]{"tests/HW5/" + name});
        }
    }
}