package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Callable;

import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenSource;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.jf.smali.LexerErrorInterface;
import org.jf.smali.smaliFlexLexer;
import org.jf.smali.smaliParser;

import antlr3.TranslateWalker;
import ast.PrettyPrintVisitor;

public class MultiThreadUtils {
	public static class ParserWorker implements Callable<CommonTree> {
		public String path;

		public ParserWorker(String path) {
			this.path = path;
		}

		@Override
		public CommonTree call() throws Exception {
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

			CommonTree tree = (CommonTree) result.getTree();
			return tree;
		}
	}

	/* *
	 * Laziness is a programmer's prime virtue, so does some memory consumers
	 * */
	public static class TranslateWorker implements Callable<ast.classs.Class> {
		public ParserWorker parserWorker;

		public TranslateWorker(ParserWorker parserWorker) {
			this.parserWorker = parserWorker;
		}

		@Override
		public ast.classs.Class call() throws Exception {
			CommonTree tree = parserWorker.call();
			CommonTreeNodeStream treeStream = new CommonTreeNodeStream(tree);

			TranslateWalker walker = new TranslateWalker(treeStream);
			return walker.smali_file();
		}
	}

	public static class PrettyPrintWorker implements Runnable {
		TranslateWorker worker;

		public PrettyPrintWorker(TranslateWorker worker) {
			this.worker = worker;
		}

		@Override
		public void run() {
			PrettyPrintVisitor ppv = new PrettyPrintVisitor();
			try {
				worker.call().accept(ppv);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}