package control;

import java.util.LinkedList;

@SuppressWarnings("unchecked")
public class CommandLine {
	static interface F<X> {
		public void f(X x);
	}

	static enum Kind {
		Empty, Bool, Int, String, StringList,
	}

	static class Arg<X> {
		String name;
		String option;
		String desription;
		Kind kind;
		F<X> action;

		public Arg(String name, String option, String description, Kind kind,
				F<X> action) {
			this.name = name;
			this.option = option;
			this.desription = description;
			this.kind = kind;
			this.action = action;
		}
	}

	private final static LinkedList<Arg<Object>> args;

	static {
		args = new util.Flist<Arg<Object>>().addAll(new Arg<Object>("ppoutput",
				"<outfile>", "set the output dir of the pretty print",
				Kind.String, new F<Object>() {
					@Override
					public void f(Object s) {
						Control.ppoutput = (String) s;
					}
				}), new Arg<Object>("apkoutput", "<outfile>",
				"set the name of dir output by apktool", Kind.String,
				new F<Object>() {
					@Override
					public void f(Object s) {
						Control.apkoutput = (String) s;
					}
				}), new Arg<Object>("worker", "<num>",
				"how many worker to spawn", Kind.Int, new F<Object>() {
					@Override
					public void f(Object i) {
						Control.numWorkers = (Integer) i;
					}
				}), new Arg<Object>("verbose", "{0|1|2}", "how verbose to be",
				Kind.Int, new F<Object>() {
					@Override
					public void f(Object n) {
						int i = (Integer) n;
						switch (i) {
						case 0:
							Control.verbose = Control.Verbose_t.Silent;
							break;
						case 1:
							Control.verbose = Control.Verbose_t.Pass;
							break;
						default:
							Control.verbose = Control.Verbose_t.Detailed;
							break;
						}
					}
				}), new Arg<Object>("dump", "<ast|sim>", "IR to pretty print",
				Kind.String, new F<Object>() {
					@Override
					public void f(Object i) {
						Control.dump = (String) i;
					}
				}), new Arg<Object>("action", "<dump|sym>", "action to take",
				Kind.String, new F<Object>() {
					@Override
					public void f(Object i) {
						Control.dump = (String) i;
					}
				}), new Arg<Object>("symoutput", "path",
				"path to output symbolic executing", Kind.String,
				new F<Object>() {
					@Override
					public void f(Object i) {
						Control.dump = (String) i;
					}
				}));
	}

	// scan the command line arguments, return the file name
	// in it. The file name should be unique.
	public static String scan(String[] cargs) {
		String filename = null;

		for (int i = 0; i < cargs.length; i++) {
			if (!cargs[i].startsWith("-")) {
				if (filename == null) {
					filename = cargs[i];
					continue;
				} else {
					System.out
							.println("Error: can only decompile one apk file a time");
					System.exit(1);
				}
			}

			boolean found = false;
			for (Arg<Object> arg : args) {
				if (!arg.name.equals(cargs[i].substring(1)))
					continue;

				found = true;
				String theArg = null;
				switch (arg.kind) {
				case Empty:
					arg.action.f(null);
					break;
				default:
					if (i >= cargs.length - 1) {
						System.out.println(arg.name + ": requires an argument");
						output();
						System.exit(1);
					}
					theArg = cargs[++i];
					break;
				}
				switch (arg.kind) {
				case Bool:
					if (theArg.equals("true"))
						arg.action.f(new Boolean(true));
					else if (theArg.equals("false"))
						arg.action.f(new Boolean(false));
					else {
						System.out.println(arg.name + ": requires a boolean");
						output();
						System.exit(1);
					}
					break;
				case Int:
					int num = 0;
					try {
						num = Integer.parseInt(theArg);
					} catch (java.lang.NumberFormatException e) {
						System.out.println(arg.name + ": requires an integer");
						output();
						System.exit(1);
					}
					arg.action.f(num);
					break;
				case String:
					arg.action.f(theArg);
					break;
				case StringList:
					String[] strArray = theArg.split(",");
					arg.action.f(strArray);
					break;
				default:
					break;
				}
				break;
			}
			if (!found) {
				System.out.println("undefined option: " + cargs[i]);
				output();
				System.exit(1);
			}
		}
		if (filename == null)
			usage();
		return filename;
	}

	private static void outputSpace(int n) {
		if (n < 0)
			util.Error.bug();

		while (n-- != 0)
			System.out.print(" ");
	}

	private static void output() {
		int max = 0;
		for (Arg<Object> a : args) {
			int current = a.name.length();
			if (a.option != null)
				current += a.option.length();
			if (current > max)
				max = current;
		}
		System.out.println("Available options:");
		for (Arg<Object> a : args) {
			int current = a.name.length();
			System.out.print("   -" + a.name + " ");
			if (a.option != null) {
				current += a.option.length();
				System.out.print(a.option);
			}
			outputSpace(max - current + 1);
			System.out.println(a.desription);
		}
	}

	private static void usage() {
		System.out.println("The Carbon. Copyright (C) 2013-, SSE of USTC.\n"
				+ "Usage: java Carbon [options] <filename>\n");
		output();
		System.exit(2);
	}
}