import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;

import util.PathVisitor;
import util.MultiThreadUtils;

public class CompilePass {
	private static ArrayList<File> getAllSmali(File startPoint) {
		ArrayList<File> result = new ArrayList<File>();
		for (File f : startPoint.listFiles()) {
			if (f.isFile() && f.getAbsolutePath().endsWith(".smali"))
				result.add(f);
			else if (f.isDirectory())
				result.addAll(getAllSmali(f));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static List<CommonTree> parseSmaliFile(ExecutorService executor,
			File path, PathVisitor visitor) throws RecognitionException,
			ExecutionException {
		List<Future<CommonTree>> tasks = new LinkedList<Future<CommonTree>>();

		ArrayList<File> files = getAllSmali(path);

		for (File f : files)
			tasks.add(executor.submit(new MultiThreadUtils.ParserWorker(
					visitor, f)));

		files = null;

		return util.MultiThreadUtils.getFutureResult(tasks);
	}

	/* *
	 * WARNING, this function will destroy it's argument
	 * */
	@SuppressWarnings("unchecked")
	public static List<ast.classs.Class> translate(ExecutorService executor,
			List<CommonTree> allAst) throws ExecutionException {
		ArrayList<Future<ast.classs.Class>> tasks = new ArrayList<Future<ast.classs.Class>>();

		while (allAst.size() > 0) {
			CommonTree tree = allAst.remove(0);
			tasks.add(executor
					.submit(new MultiThreadUtils.TranslateWorker(tree)));
		}

		allAst = null;

		return util.MultiThreadUtils.getFutureResult(tasks);
	}

	public static void prettyPrint(ExecutorService executor,
			List<ast.classs.Class> allAst) throws RecognitionException,
			ExecutionException {
		ArrayList<Future<Void>> tasks = new ArrayList<Future<Void>>();

		for (ast.classs.Class clazz : allAst)
			tasks.add(executor.submit(new MultiThreadUtils.PrettyPrintWorker(
					clazz)));

		allAst = null;

		util.MultiThreadUtils.getFutureResult(tasks);
	}
}
