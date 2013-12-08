import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import control.CommandLine;

public class Carbon {
	static Carbon carbon;
	static CommandLine cmd;
	static InputStream fstream;
	public ast.program.T theAst;

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

	public static void main(String[] args) throws IOException,
			InterruptedException {
		carbon = new Carbon();
		cmd = new CommandLine();

		if (args.length != 1) {
			System.err.println("Usage: java -cp bin Carbon A.apk");
			System.exit(1);
		}
		String fname = args[0];
		executeInShell("java -jar jar/apktool.jar d " + fname + " output",
				System.out, System.err);
	}
}
