package sym;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;

import sym.op.Array;
import sym.pred.IPrediction;
import clojure.lang.IPersistentMap;
import clojure.lang.MapEntry;
import clojure.lang.PersistentVector;

/* *
 * Interface to talk to Z3
 * */
public class Z3Stub {
	/* *
	 * `calculate` must be synchronized, because this method will call by
	 * multiple thread
	 */

	public static class Z3Result {
		public HashMap<String, String> result;
		public boolean satOrNot;
	}

	public static synchronized Z3Result calculate(IPersistentMap mapToSym,
			PersistentVector conditions) {
		HashMap<String, String> v = new HashMap<String, String>();
		Z3Result z3result = new Z3Result();
		Process p;
		try {
			p = Runtime.getRuntime().exec("z3 -smt2 -in");
			OutputStream out = p.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					out));
			@SuppressWarnings("rawtypes")
			Iterator it = mapToSym.iterator();
			while (it.hasNext()) {
				MapEntry me = (MapEntry) it.next();
				String s = (String) me.key();
				writer.write("(declare-const " + s + " Int)");
			}
			it = conditions.iterator();
			while (it.hasNext()) {
				sym.pred.IPrediction cond = (IPrediction) it.next();
				// IOp cond = (IOp) it.next();
				if (cond instanceof Array) {
					// array
				} else {
					// int
					writer.write("(assert " + cond.toString() + ")");
				}
			}
			writer.write("(check-sat)");
			writer.write("(get-model)"); // now it can only support sat
			writer.close();
			out.close();
			InputStream in = p.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line.contains("unsat")) {
					z3result.satOrNot = false;
					break;
				}
				if (line.indexOf("(define-fun") != -1) {
					int t = line.indexOf("(define-fun");
					line = line.substring(t + 12, line.length());
					String st = "";
					for (int i = 0; i < line.length(); i++) {
						if (line.charAt(i) != ' ') {
							char c = line.charAt(i);
							st += c;
						} else {
							line = reader.readLine();
							String ss = "";
							for (int j = 0; j < line.length(); j++) {
								char c = line.charAt(j);
								if (c >= '0' && c <= '9') {
									ss += c;
									v.put(st, ss);
									z3result.result = v;
									z3result.satOrNot = true;
								}
								if (c == ')')
									break;
							}
							break;
						}
					}
				}
			}
			p.waitFor();
			in.close();
			reader.close();
			p.destroy();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return z3result;
	}
}
