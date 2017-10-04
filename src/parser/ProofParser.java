package parser;

import expressions.Expression;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by Алексей on 01.01.2015.
 */
public interface ProofParser {
    List<Expression> parse(File f) throws FileNotFoundException, ParserException;
}
