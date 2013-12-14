import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.LinkedList;
import util.PathVisitor;
import org.antlr.runtime.tree.CommonTree;

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

	public static List<CommonTree> forEachFile(File path, PathVisitor visitor) throws org.antlr.runtime.RecognitionException {
		List<CommonTree> list = new LinkedList<CommonTree>();
		File[] result = path.listFiles();
		for (int i = 0; i < result.length; i++) {
			if (result[i].isFile()) {
				CommonTree ct = visitor.visit(result[i].getAbsolutePath());
				if (ct != null)
					list.add(ct);
			} else if (result[i].isDirectory())
				list.addAll(forEachFile(result[i], visitor));
		}
		return list;
	}

	public static void main(String[] args) throws IOException,
			InterruptedException, org.antlr.runtime.RecognitionException {
		carbon = new Carbon();
		// cmd = new CommandLine();

		if (args.length != 1) {
			System.err.println("Usage: java -cp bin Carbon A.apk");
			System.exit(1);
		}
		String fname = args[0];
		executeInShell("java -jar jar/apktool.jar d " + fname + " output",
				System.out, System.err);

		List<CommonTree> allAst = forEachFile(new File("output"), new PathVisitor());
		for (CommonTree ct: allAst)
			System.out.println(ct.toStringTree());
	}
}
