package util;

import org.antlr.runtime.*;
import org.antlr.runtime.tree.CommonTree;
import org.jf.smali.*;

import java.io.*;

public class PathVisitor {
	public CommonTree visit(String path) {
		if (!path.endsWith(".smali"))
			return null;

		CommonTokenStream tokens;

		LexerErrorInterface lexer;
		File smaliFile = new File(path);

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(smaliFile.getAbsolutePath());
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(fis, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		lexer = new smaliFlexLexer(reader);
		// ((smaliFlexLexer)lexer).setSourceFile(smaliFile);
		tokens = new CommonTokenStream((TokenSource) lexer);

		smaliParser parser = new smaliParser(tokens);
		parser.setVerboseErrors(true);

		smaliParser.smali_file_return result = null;
		try {
			result = parser.smali_file();
		} catch (RecognitionException e) {
			e.printStackTrace();
		}

		if (parser.getNumberOfSyntaxErrors() > 0
				|| lexer.getNumberOfSyntaxErrors() > 0)
			System.out.println("return false");

		return (CommonTree) result.getTree();
	}
}
