import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import control.CommandLine;
import control.Control;
import util.MultiThreadUtils.SimplifyWorker;
import util.MultiThreadUtils.TranslateWorker;
import util.MultiThreadUtils.ParserWorker;
import sym.SymbolicExecutor;

public class Silicon {
	static Silicon silicon;

	public static int executeInShell(String cmd, PrintStream stdout, PrintStream stderr)
			throws IOException, InterruptedException {
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

	@SuppressWarnings({ "unused", "finally" })
	public String getMain() {

		String action = null;
		String packagee = null;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dbBuilder;
		try {
			dbBuilder = dbFactory.newDocumentBuilder();
			Document doc = null;
			doc = dbBuilder.parse("/tmp/output/AndroidManifest.xml");
			NodeList alist = doc.getElementsByTagName("manifest");
			OK: for (int i = 0; i < alist.getLength(); i++) {
				Element element = (Element) alist.item(i);
				packagee = element.getAttribute("package");
				packagee = packagee.replace('.', '/');
				Control.entryPoint = packagee;
				NodeList blist = element.getElementsByTagName("application");
				for (int j = 0; j < blist.getLength(); j++) {
					Element belement = (Element) blist.item(j);
					NodeList clist = belement.getElementsByTagName("activity");
					for (int z = 0; z < clist.getLength(); z++) {
						Element celement = (Element) clist.item(z);
						NodeList dlist = celement.getElementsByTagName("intent-filter");
						for (int k = 0; k < dlist.getLength(); k++) {
							Element delement = (Element) dlist.item(i);
							NodeList slist = delement.getElementsByTagName("action");
							for (int s = 0; s < slist.getLength(); s++) {
								Element selement = (Element) clist.item(s);
								action = selement.getAttribute("android:name");
								action = action.substring(1);
								break OK;
							}
						}
					}
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return packagee + "/" + action;
		}
	}

	public void run(String[] args) throws IOException, InterruptedException,
			org.antlr.runtime.RecognitionException, ExecutionException,
			ClassNotFoundException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Control.fileName = CommandLine.scan(args);

		String apktoolCMD = "java -jar jar/apktool.jar d -f "
				+ Control.fileName + " " + Control.apkoutput;
		executeInShell(apktoolCMD, System.out, System.err);

		List<ParserWorker> workers;
		workers = CompilePass.parseSmaliFile(new File(Control.apkoutput));

		List<TranslateWorker> classes;
		classes = CompilePass.translate(workers);
		workers = null;

		if (Control.action.equals("dump")) {
			if (Control.dump.equals("ast")) {
				CompilePass.prettyPrint(classes);
			} else if (Control.dump.equals("sim")) {
				List<SimplifyWorker> sims;
				sims = CompilePass.simplify(classes);
				classes = null;

				CompilePass.prettyPrintSim(sims);
			} else {
				System.err.println("unknow dump args " + Control.dump);
				System.exit(2);
			}
		} else if (Control.action.equals("sym")) {
			List<SimplifyWorker> sims;
			sims = CompilePass.simplify(classes);
			classes = null;

			if (Control.entryPoint == null) {
				System.err.println("you need to specify the main Class using -entry");
				System.exit(2);
			}
			//Control.entryPoint = getMain();
			SymbolicExecutor symbolicExe = new SymbolicExecutor(sims, new File(
					Control.symoutput), Control.apkoutput, Control.entryPoint);
			symbolicExe.execute();
		} else {
			System.err.println("unknow action args " + Control.dump);
			System.exit(2);
		}
	}

	public static void main(String[] args) {
		try {
			silicon = new Silicon();
			silicon.run(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}