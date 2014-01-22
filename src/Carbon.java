import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.ExecutionException;

import control.CommandLine;
import control.Control;
import util.MultiThreadUtils.SimplifyWorker;
import util.MultiThreadUtils.TranslateWorker;
import util.MultiThreadUtils.ParserWorker;

public class Carbon {
	static Carbon carbon;

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

		String apktoolCMD = "java -jar jar/apktool.jar d -f "
				+ Control.fileName + " " + Control.apkoutput;
		executeInShell(apktoolCMD, System.out, System.err);

		List<ParserWorker> workers;
		workers = CompilePass.parseSmaliFile(new File(Control.apkoutput));

		List<TranslateWorker> classes;
		classes = CompilePass.translate(workers);
		workers = null;

		List<SimplifyWorker> sims;
		sims = CompilePass.simplify(classes);
		classes = null;

		CompilePass.prettyPrintSim(sims);
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