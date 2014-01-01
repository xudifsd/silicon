package util;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;

import antlr3.TranslateWalker;
import ast.PrettyPrintVisitor;
import ast.classs.Class;

public class MultiThreadUtils {
	public static class ParserWorker implements Callable<CommonTree> {
		PathVisitor visitor;
		File f;

		public ParserWorker(PathVisitor visitor, File f) {
			this.visitor = visitor;
			this.f = f;
		}

		@Override
		public CommonTree call() throws Exception {
			return visitor.visit(f.getAbsolutePath());
		}
	}

	public static class TranslateWorker implements Callable<ast.classs.Class> {
		CommonTree tree;

		public TranslateWorker(CommonTree tree) {
			this.tree = tree;
		}

		@Override
		public ast.classs.Class call() throws Exception {
			CommonTreeNodeStream treeStream = new CommonTreeNodeStream(tree);

			TranslateWalker walker = new TranslateWalker(treeStream);
			return walker.smali_file();
		}
	}

	public static class PrettyPrintWorker implements Callable<Void> {
		ast.classs.Class clazz;

		public PrettyPrintWorker(Class clazz) {
			super();
			this.clazz = clazz;
		}

		@Override
		public Void call() throws Exception {
			PrettyPrintVisitor ppv = new PrettyPrintVisitor();
			clazz.accept(ppv);
			return null;
		}
	}

	/* *
	 * WARNING, this function will destroy it's argument
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List getFutureResult(List list) throws ExecutionException {
		List result = new LinkedList();
		while (list.size() > 0) {
			Future task = (Future) list.remove(0);
			while (true) {
				if (task.isDone()) {
					Object obj;
					try {
						obj = task.get();
					} catch (InterruptedException e) {
						continue;
					}
					result.add(obj);
					break;
				}
			}
		}
		return result;
	}
}