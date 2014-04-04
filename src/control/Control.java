package control;

public class Control {
	// source file
	public static String fileName = null;

	public static String ppoutput = "/tmp/smalioutput";

	public static String apkoutput = "/tmp/output";

	public static String symoutput = "/tmp/symoutput";

	public static int numWorkers = 4;

	public static String dump = "sim";

	public static String action = "dump";

	public static int symExeSec = 60; // default to execute at most 60 seconds

	public static String entryPoint = null;

	// verbose level
	public enum Verbose_t {
		Silent, Pass, Detailed
	}

	public static Verbose_t verbose = Verbose_t.Silent;
}