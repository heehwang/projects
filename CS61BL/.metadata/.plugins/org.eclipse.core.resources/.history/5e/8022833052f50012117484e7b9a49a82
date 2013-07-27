import java.io.*;
import java.util.*;

public class MyStringClass implements Comparable {
	protected String myString;

	public MyStringClass (String s) {
		myString = s;
	}

	// Some hash functions for testing.
	public int hashCodeA ( ) { // to be used in experiment 4
		return myString.charAt (0);
	}
	public int hashCodeB ( ) { // to be used in experiment 5
		int sum = 0;
		for (int k = 0; k < myString.length ( ); k++) {
			sum += myString.charAt (k);
		}
		return sum;
	}
	public int hashCodeC ( ) { // to be used in experiment 6.
		return myString.hashCode ( );
	}
	public int compareTo(Object arg0) {
		return this.myString.compareTo(((MyStringClass) arg0).myString);
	}

	// Preconditions: obj != null; obj's class is a subclass of TestString.
	// Returns true when this object's string matches that of obj.
	public boolean equals (Object obj) { // to be fixed in experiment 1
		MyStringClass objTestString = (MyStringClass) obj;
		boolean result = this.myString.equals (objTestString.myString);
		return result;
	}

	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.err.println("Not enough arguments.");
			System.err.println("Usage:"
					+ "java TestString wordFile outputFile");
			System.exit(1);
		}
		PrintStream counts = new PrintStream(new FileOutputStream(new File(
				args[1])));
		for (int i = 250; i < 25000; i = i + 250) {

			BufferedReader strings = null;
			try {
				strings = new BufferedReader(new InputStreamReader(
						new FileInputStream(new File(args[0]))));
			} catch (IOException e) {
				System.err.println("Can't open file: " + args[0]);
				System.exit(1);
			}
			TreeMap<MyStringClass, Integer> table = new TreeMap<MyStringClass, Integer>();
			long startTime = System.currentTimeMillis();
			String s = strings.readLine();
			for (int k = 0; (s != null) && (k < i); s = strings.readLine(), k++) {
				table.put(new MyStringClass(s), new Integer(s.length()));
			}
			long runTime = System.currentTimeMillis() - startTime;
			counts.println("" + i + " " + runTime);
		}
		counts.close();
		System.out.println("Done");
	}
	
}
