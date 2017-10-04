package parser;

import deduction.DeductionsExpressions;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Алексей on 01.01.2015.
 */
public interface ProofParserD {
    DeductionsExpressions parse(File f) throws FileNotFoundException, ParserException;
}
