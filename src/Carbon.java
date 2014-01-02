import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.antlr.runtime.tree.CommonTree;

import control.CommandLine;
import control.Control;
import util.PathVisitor;

public class Carbon {
	static Carbon carbon;
	ExecutorService executor;

	public static int executeInShell(String cmd, PrintStream stdout,
			PrintStream stderr) throws IOException, InterruptedException {
		Process p = Runtime.getRuntime().exec(cmd);

		byte[] buffer = new byte[1024];
		int num = p.getInputStream().read(buffer);
		while (num != -1) {
			stdout.write(buffer, 0, num);
			num = p.getInputStream().read(buffer);
		}

		num = p.getErrorStream().read(buffer);
		while (num != -1) {
			stderr.write(buffer, 0, num);
			num = p.getErrorStream().read(buffer);
		}

		p.waitFor();
		return p.exitValue();
	}

	public void run(String[] args) throws IOException, InterruptedException,
			org.antlr.runtime.RecognitionException, ExecutionException {
		Control.fileName = CommandLine.scan(args);
		executor = Executors.newFixedThreadPool(Control.numWorkers);

		String apktoolCMD = "java -jar jar/apktool.jar d -f "
				+ Control.fileName + " " + Control.apkoutput;
		executeInShell(apktoolCMD, System.out, System.err);

		List<CommonTree> allAst;
		allAst = CompilePass.parseSmaliFile(executor, new File(
				Control.apkoutput), new PathVisitor());

		List<ast.classs.Class> classes;

		// WARNING, CompilePass.translate() will destroy allAst
		classes = CompilePass.translate(executor, allAst);
		allAst = null;

		CompilePass.prettyPrint(executor, classes);
		executor.shutdown();
	}

	public static void main(String[] args) {
		try {
			carbon = new Carbon();
			carbon.run(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}